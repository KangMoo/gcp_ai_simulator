package simulator.scenario.handler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import simulator.scenario.ScenarioRunner;
import simulator.scenario.handler.base.PhaseHandler;
import simulator.scenario.phase.element.ExtractNode;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kangmoo Heo
 */
@Slf4j
@Getter
@Setter
public class ExtractHandler extends PhaseHandler<ExtractNode> {
    private static final Pattern NUM_PATTERN = Pattern.compile("(\\d+)");

    private boolean success = false;

    public ExtractHandler(ScenarioRunner scenarioRunner) {
        super(scenarioRunner);
        if (phase.getNextPhaseIfSuccess() == null) phase.setNextPhaseIfSuccess(phase.getNextPhase());
        if (phase.getNextPhaseIfFail() == null) phase.setNextPhaseIfFail(phase.getNextPhase());
    }

    @Override
    public void handle() {
        switch (phase.getType()) {
            case "number":
                String target = scenarioInfo.getVariables().get(phase.getTarget());
                String result = Optional.of(NUM_PATTERN.matcher(target)).filter(Matcher::find).map(o -> o.group(1)).orElse(null);
                success = (result != null);
                if (!success) return;
                scenarioInfo.getVariables().put(phase.getVariable(), result);
                break;
            default:
                throw new NullPointerException("Extract Type is Unknown [" + phase.getType() + "]");
        }
    }

    @Override
    public String getNextPhase() {
        return success ? phase.getNextPhaseIfSuccess() : phase.getNextPhaseIfFail();
    }
}
