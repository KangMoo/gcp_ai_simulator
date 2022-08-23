package com.uangel.simulator.scenario.handler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.uangel.simulator.scenario.ScenarioRunner;
import com.uangel.simulator.scenario.handler.base.PhaseHandler;
import com.uangel.simulator.scenario.phase.element.StartNode;

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
        log.info("({}) Scenario Start!", this.scenarioRunner.getId());
    }

}
