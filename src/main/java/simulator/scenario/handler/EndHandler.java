package simulator.scenario.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import simulator.scenario.ScenarioInfo;
import simulator.scenario.handler.base.PhaseHandler;
import simulator.scenario.phase.base.Phase;
import simulator.scenario.phase.element.EndNode;
import simulator.scenario.phase.element.SaveNode;

import java.util.Collection;
import java.util.Optional;

/**
 * @author kangmoo Heo
 */
@Slf4j
@AllArgsConstructor
@Data
public class EndHandler implements PhaseHandler {
    @NonNull
    private ScenarioInfo scenarioInfo;
    @NonNull
    private EndNode phase;

    @Override
    public void handle() {
        Optional.of(phase)
                .map(EndNode::getSaveNode)
                .map(SaveNode::getVariables)
                .stream().flatMap(Collection::stream)
                .forEach(o -> scenarioInfo.getSavedData().put(o, scenarioInfo.getVariables().get(o)));
        log.info("Scenario End!");
    }

    @Override
    public Phase getNextPhase() {
        return null;
    }
}
