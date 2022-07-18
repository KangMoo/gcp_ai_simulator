package simulator.scenario.handler;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import simulator.scenario.ScenarioInfo;
import simulator.scenario.handler.base.PhaseHandler;
import simulator.scenario.phase.base.Phase;
import simulator.scenario.phase.element.FilterNode;

/**
 * @author kangmoo Heo
 */
@Slf4j
@Data
@RequiredArgsConstructor
public class FilterHandler implements PhaseHandler {
    @NonNull
    private ScenarioInfo scenarioInfo;
    @NonNull
    private FilterNode phase;
    private boolean success;
    private String mappedVar;
    private String target;

    @Override
    public void handle() {
        this.target = scenarioInfo.getVariables().get(phase.getTarget());
        switch (phase.getConditionNode().getType()) {
            case "contains":
                containsCheck();
                break;
            default:
                throw new NullPointerException("Extract Type is Unknown [" + phase.getConditionNode().getType() + "]");
        }
    }

    public void containsCheck() {
        this.mappedVar = phase.getConditionNode().getItems().stream().filter(target::contains).findAny().orElse(null);
        this.success = mappedVar != null;
        if (success) {
            this.scenarioInfo.getVariables().put(phase.getVariable(), mappedVar);
        }
        log.debug("Filter result [{}]", this.success ? "Success" : "Fail");
    }

    @Override
    public Phase getNextPhase() {
        return success ?
                scenarioInfo.findPhase(phase.getNextPhaseIfSuccess()) :
                scenarioInfo.findPhase(phase.getNextPhaseIfFail());
    }
}
