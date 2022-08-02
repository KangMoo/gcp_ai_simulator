package simulator.scenario.handler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import simulator.scenario.ScenarioRunner;
import simulator.scenario.handler.base.PhaseHandler;
import simulator.scenario.phase.element.FilterNode;

/**
 * @author kangmoo Heo
 */
@Slf4j
@Getter
@Setter
public class FilterHandler extends PhaseHandler<FilterNode> {
    private boolean success;
    private String mappedVar;
    private String target;

    public FilterHandler(ScenarioRunner scenarioRunner) {
        super(scenarioRunner);
        if(phase.getNextPhaseIfSuccess() == null) phase.setNextPhaseIfSuccess(phase.getNextPhase());
        if(phase.getNextPhaseIfFail() == null) phase.setNextPhaseIfFail(phase.getNextPhase());
    }

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
    public String getNextPhase() {
        return success ? phase.getNextPhaseIfSuccess() : phase.getNextPhaseIfFail();
    }
}
