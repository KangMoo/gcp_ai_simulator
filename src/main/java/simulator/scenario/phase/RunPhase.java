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
public class RunPhase extends Phase {
    private final String value;

    public RunPhase(Scenario scenario, String value) {
        super(scenario);
        this.value = value;
    }

    @Override
    public void run() throws Exception{
        scenario.getEngine().eval(value);
    }
}
