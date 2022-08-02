package simulator.scenario.handler;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import simulator.scenario.ScenarioInfo;
import simulator.scenario.ScenarioRunner;
import simulator.scenario.handler.base.PhaseHandler;
import simulator.scenario.phase.base.Phase;
import simulator.scenario.phase.element.StartNode;

/**
 * @author kangmoo Heo
 */
@Slf4j
@Getter
@Setter
public class StartHandler extends PhaseHandler<StartNode> {

    public StartHandler(ScenarioRunner scenarioRunner) {
        super(scenarioRunner);
    }

    @Override
    public void handle() {
        log.info("Scenario Start!");
    }

}
