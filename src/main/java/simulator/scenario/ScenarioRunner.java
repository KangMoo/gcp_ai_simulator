package simulator.scenario;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import simulator.scenario.handler.*;
import simulator.scenario.handler.base.PhaseHandler;
import simulator.scenario.phase.base.Phase;
import simulator.scenario.phase.element.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;


/**
 * @author kangmoo Heo
 */
@Slf4j
@RequiredArgsConstructor
@Getter
@Setter
public class ScenarioRunner {
    @NonNull
    private ScenarioInfo scenarioInfo;

    @NonNull
    private ScheduledExecutorService executor;

    private String id = UUID.randomUUID().toString();

    private boolean scenarioComplete;

    private Phase currentPhase;
    private Runnable onScenarioDone;
    private CompletableFuture<ScenarioInfo> result;

    public Future<ScenarioInfo> scenarioRun() {
        result = new CompletableFuture<>();
        Phase startPhase = scenarioInfo.getPhases().values().stream().filter(StartNode.class::isInstance).findAny()
                .orElseThrow(() -> new NullPointerException("Start Phase is null."));

        runPhase(startPhase);
        return result;
    }


    public PhaseHandler<? extends Phase> buildPhaseHandler(Phase phase) {
        if (phase instanceof EndNode) {
            return new EndHandler(this);
        } else if (phase instanceof ExtractNode) {
            return new ExtractHandler(this);
        } else if (phase instanceof FilterNode) {
            return new FilterHandler(this);
        } else if (phase instanceof SelectNode) {
            return new SelectHandler(this);
        } else if (phase instanceof StartNode) {
            return new StartHandler(this);
        } else if (phase instanceof SttNode) {
            return new SttHandler(this);
        } else if (phase instanceof TtsNode) {
            return new TtsHandler(this);
        } else {
            log.warn("({}) Unknown instance type [{}]", this.id, phase.getClass().getName());
            return null;
        }
    }

    public void runPhase(Phase phase) {
        log.debug("({}) Phase Start [{}]", this.id, phase.getId());
        executor.execute(() -> {
            try {
                this.currentPhase = phase;
                this.buildPhaseHandler(phase).proc();
            } catch (Exception e) {
                stop();
            }
        });
    }

    public void stop() {
        log.info("({}) Scenario End", this.id);
        this.scenarioComplete = true;
        this.result.complete(scenarioInfo);
        if (this.onScenarioDone != null) onScenarioDone.run();
    }
}
