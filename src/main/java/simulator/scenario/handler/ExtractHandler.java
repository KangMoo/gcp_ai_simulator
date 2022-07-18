package simulator.scenario.handler;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import simulator.scenario.ScenarioInfo;
import simulator.scenario.handler.base.PhaseHandler;
import simulator.scenario.phase.base.Phase;
import simulator.scenario.phase.element.ExtractNode;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kangmoo Heo
 */
@Slf4j
@Data
@RequiredArgsConstructor
public class ExtractHandler implements PhaseHandler {
    private static final Pattern NUM_PATTERN = Pattern.compile("(\\d+)");
    @NonNull
    private ScenarioInfo scenarioInfo;
    @NonNull
    private ExtractNode phase;

    private boolean success = false;

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
    public Phase getNextPhase() {
        return success ?
                scenarioInfo.findPhase(phase.getNextPhaseIfSuccess()) :
                scenarioInfo.findPhase(phase.getNextPhaseIfFail());
    }
}
