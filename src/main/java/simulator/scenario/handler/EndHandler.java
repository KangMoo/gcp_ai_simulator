package simulator.scenario.handler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import simulator.scenario.ScenarioRunner;
import simulator.scenario.handler.base.PhaseHandler;
import simulator.scenario.phase.element.EndNode;
import simulator.scenario.phase.element.SaveNode;

import java.util.Collection;
import java.util.Optional;

/**
 * @author kangmoo Heo
 */
@Slf4j
@Getter
@Setter
public class EndHandler extends PhaseHandler<EndNode> {

    public EndHandler(ScenarioRunner scenarioRunner) {
        super(scenarioRunner);
        setOnProcSuccess(scenarioRunner::stop);
    }

    @Override
    public void handle() {
        Optional.of(phase)
                .map(EndNode::getSaveNode)
                .map(SaveNode::getVariables)
                .stream().flatMap(Collection::stream)
                .forEach(o -> scenarioInfo.getSavedData().put(o, scenarioInfo.getVariables().get(o)));
        log.info("({}) Scenario End!", this.scenarioRunner.getId());
    }

}
