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

import java.util.concurrent.atomic.AtomicReference;


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

    public ScenarioInfo scenarioRun() {
        try {
            AtomicReference<Phase> nowPhase = new AtomicReference<>();
            nowPhase.set(scenarioInfo.getPhases().values().stream().filter(StartNode.class::isInstance).findAny().orElse(null));

            while (nowPhase.get() != null) {
                Phase nextPhase = runPhase(scenarioInfo, nowPhase.get());
                nowPhase.set(nextPhase);
            }
            scenarioInfo.getLocalSound().stop();
        } catch (Exception e) {
            log.warn("Err Occurs", e);
        } finally {
            scenarioInfo.close();
        }
        return scenarioInfo;
    }

    public Phase runPhase(ScenarioInfo scenarioInfo, Phase phase) {
        try {
            log.debug("{} Phase Start", phase.getId());
            PhaseHandler handler;
            if (phase instanceof EndNode) {
                handler = new EndHandler(scenarioInfo, (EndNode) phase);
            } else if (phase instanceof ExtractNode) {
                handler = new ExtractHandler(scenarioInfo, (ExtractNode) phase);
            } else if (phase instanceof FilterNode) {
                handler = new FilterHandler(scenarioInfo, (FilterNode) phase);
            } else if (phase instanceof SelectNode) {
                handler = new SelectHandler(scenarioInfo, (SelectNode) phase);
            } else if (phase instanceof StartNode) {
                handler = new StartHandler(scenarioInfo, (StartNode) phase);
            } else if (phase instanceof SttNode) {
                handler = new SttHandler(scenarioInfo, (SttNode) phase);
            } else if (phase instanceof TtsNode) {
                handler = new TtsHandler(scenarioInfo, (TtsNode) phase);
            } else {
                log.error("Unknown instance type [{}]", phase.getClass().getName());
                return null;
            }
            handler.handle();
            log.debug("{} Phase Done", phase.getId());
            return handler.getNextPhase();
        } catch (Exception e) {
            log.error("ERR Occurs [{}]", phase.getId(), e);
            return null;
        }
    }
}
