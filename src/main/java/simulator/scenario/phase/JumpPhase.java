package simulator.scenario.phase;

import lombok.Getter;
import lombok.ToString;
import simulator.scenario.phase.base.Phase;
import simulator.scenario.Scenario;

import java.util.List;

/**
 * @author kangmoo Heo
 */
@Getter
@ToString
public class JumpPhase extends Phase {
    private final String target;

    public JumpPhase(Scenario scenario, String target) {
        super(scenario);
        this.target = target;
    }

    @Override
    public void run() throws Exception {
        List<Phase> phases = scenario.getPhases();
        for (int i = 0; i < phases.size(); i++) {
            if (phases.get(i) instanceof MarkPhase && ((MarkPhase) phases.get(i)).getValue().equals(target)) {
                scenario.getCurrentPos().set(i);
                return;
            }
        }
    }
}
