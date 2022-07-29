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

import java.util.concurrent.*;


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

    // @NonNull
    private String id;

    // @NonNull
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private Phase currentPhase;

    private boolean scenarioEnd = false;
    private Runnable onScenarioEnd;

    private CompletableFuture<ScenarioInfo> result;
    public Future<ScenarioInfo> scenarioRun() {
        result = new CompletableFuture<>();
        try {
            Phase startPhase = scenarioInfo.getPhases().values().stream().filter(StartNode.class::isInstance).findAny().orElse(null);
            if (startPhase == null) {
                log.warn("Start Phase is null.");
                stop();
                return result;
            }
            runPhase(startPhase);
        } catch (Exception e) {
            log.warn("Err Occurs", e);
            stop();
        }
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
            log.error("Unknown instance type [{}]", phase.getClass().getName());
            return null;
        }
    }

    public void runPhase(Phase phase) {
        if(phase == null) {
            stop();
            return;
        }

        log.debug("{} Phase Start", phase.getId());
        executor.execute(() -> {
            try {
                this.currentPhase = phase;
                this.buildPhaseHandler(phase).proc();
                log.debug("{} Phase Done", phase.getId());
            } catch (Exception e) {
                log.error("{} Phase Fail. ERR Occurs", phase.getId(), e);
                stop();
            }
        });
    }

    public void stop() {
        this.scenarioEnd = true;
        this.result.complete(scenarioInfo);
        if (onScenarioEnd != null) onScenarioEnd.run();

    }
}
