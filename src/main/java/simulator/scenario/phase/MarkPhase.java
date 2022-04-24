package simulator.scenario.phase;

import lombok.Getter;
import lombok.ToString;
import simulator.scenario.phase.base.Phase;
import simulator.scenario.Scenario;

/**
 * @author kangmoo Heo
 */
@Getter
@ToString
public class MarkPhase extends Phase {
    private final String value;

    public MarkPhase(Scenario scenario, String value) {
        super(scenario);
        this.value = value;
    }

    @Override
    public void run() throws Exception {
        // Do nothing
    }
}
