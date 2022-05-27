package simulator.scenario.phase;

import lombok.Getter;
import lombok.ToString;
import simulator.scenario.Scenario;
import simulator.scenario.phase.base.Phase;

/**
 * @author kangmoo Heo
 */
@Getter
@ToString
public class IfPhase extends Phase {
    private final String expression;
    private final JumpPhase jumpPhase;

    public IfPhase(Scenario scenario, String expression, JumpPhase jumpPhase) {
        super(scenario);
        this.expression = expression;
        this.jumpPhase = jumpPhase;
    }

    @Override
    public void run() throws Exception {
        if ((boolean) scenario.getEngine().eval(expression)) jumpPhase.run();
    }
}
