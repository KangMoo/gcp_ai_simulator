package simulator.scenario.handler.base;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import simulator.scenario.ScenarioInfo;
import simulator.scenario.ScenarioRunner;
import simulator.scenario.phase.base.Phase;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

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
    private Runnable onProcSuccess = () -> Optional.ofNullable(getNextPhase()).ifPresent(p -> scenarioRunner.runPhase(scenarioInfo.getPhases().get(p)));
    private Consumer<Exception> onProcFail = e -> {
        if (e != null) {
            log.warn("({}) Err Occurs [{}]", this.scenarioRunner.getId(), this.phase.getId(), e);
        }
        scenarioRunner.stop();
    };

    protected PhaseHandler(ScenarioRunner scenarioRunner) {
        this.scenarioRunner = scenarioRunner;
        this.scenarioInfo = scenarioRunner.getScenarioInfo();
        this.phase = (T) scenarioRunner.getCurrentPhase();
        this.executor = scenarioRunner.getExecutor();
    }

    public void proc() {
        try {
            handle();
            procDoneSuccess();
        } catch (Exception e) {
            procDoneFail(e);
        }
    }

    public void procDoneSuccess() {
        this.onProcSuccess.run();
    }

    public void procDoneFail() {
        this.procDoneFail((Exception) null);
    }

    public void procDoneFail(String reason) {
        this.procDoneFail(new RuntimeException(reason));
    }

    public void procDoneFail(Exception e) {
        this.onProcFail.accept(e);
    }

    protected abstract void handle() throws IOException;

    protected String getNextPhase() {
        return phase.getNextPhase();
    }


}
