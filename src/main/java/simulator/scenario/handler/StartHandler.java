package simulator.scenario.handler;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import simulator.scenario.ScenarioInfo;
import simulator.scenario.handler.base.PhaseHandler;
import simulator.scenario.phase.base.Phase;
import simulator.scenario.phase.element.StartNode;

/**
 * @author kangmoo Heo
 */
@Slf4j
@RequiredArgsConstructor
@Data
public class StartHandler implements PhaseHandler {
    @NonNull
    private ScenarioInfo scenarioInfo;
    @NonNull
    private StartNode phase;

    @Override
    public void handle() {
        log.info("Scenario Start!");
    }

    @Override
    public Phase getNextPhase() {
        return scenarioInfo.findPhase(phase.getNextPhase());
    }
}
