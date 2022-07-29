package simulator.scenario.handler.base;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import simulator.scenario.ScenarioInfo;
import simulator.scenario.ScenarioRunner;
import simulator.scenario.phase.base.Phase;

import java.util.concurrent.ScheduledExecutorService;

/**
 * @author kangmoo Heo
 */
@Slf4j
@Getter
@Setter
public abstract class PhaseHandler<T extends Phase> {
    protected ScenarioRunner scenarioRunner;
    protected ScenarioInfo scenarioInfo;
    protected T phase;
    protected ScheduledExecutorService executor;

    protected PhaseHandler(ScenarioRunner scenarioRunner) {
        this.scenarioRunner = scenarioRunner;
        this.scenarioInfo = scenarioRunner.getScenarioInfo();
        this.phase = (T) scenarioRunner.getCurrentPhase();
        this.executor = scenarioRunner.getExecutor();
    }

    public void runNextPhase() {
        scenarioRunner.runPhase(getNextPhase());
    }

    public void proc() {
        try {
            handle();
            scenarioRunner.runPhase(getNextPhase());
        } catch (Exception e) {
            log.warn("{} Err Occurs", phase.getId(), e);
            scenarioRunner.stop();
        }
    }

    protected abstract void handle() throws Exception;

    protected abstract Phase getNextPhase();
}
