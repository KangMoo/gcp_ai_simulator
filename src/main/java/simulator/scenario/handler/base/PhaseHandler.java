package simulator.scenario.handler.base;

import simulator.scenario.phase.base.Phase;

/**
 * @author kangmoo Heo
 */
public interface PhaseHandler {
    void handle() throws Exception;

    Phase getNextPhase();
}
