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
public class SleepPhase extends Phase {
    private final long timeMs;

    public SleepPhase(Scenario scenario, long timeMs) {
        super(scenario);
        this.timeMs = timeMs;
    }

    @Override
    public void run() throws Exception{
        Thread.sleep(timeMs);
    }
}
