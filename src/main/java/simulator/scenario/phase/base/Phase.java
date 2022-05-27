package simulator.scenario.phase.base;

import simulator.scenario.Scenario;

/**
 * @author kangmoo Heo
 */

public abstract class Phase {
    protected final Scenario scenario;

    protected Phase(Scenario scenario) {
        this.scenario = scenario;
    }

    public abstract void run() throws Exception;
}
