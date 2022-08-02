package simulator.scenario.handler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import simulator.scenario.ScenarioBuilder;
import simulator.scenario.ScenarioInfo;
import simulator.scenario.ScenarioRunner;
import simulator.scenario.handler.base.PhaseHandler;
import simulator.scenario.phase.base.Phase;
import simulator.scenario.phase.element.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author kangmoo Heo
 */
@Slf4j
@Getter
@Setter
public class SelectHandler extends PhaseHandler<SelectNode> {
    private AtomicInteger tryCount = new AtomicInteger();

    private ScenarioRunner subScenarioRunner;

    private String target;
    private FilterNode filterNode;

    public SelectHandler(ScenarioRunner scenarioRunner) {
        super(scenarioRunner);

        this.filterNode = this.phase.getFilterNode();
        this.target = this.filterNode.getVariable();

        Optional<TtsNode> ttsPlayItem;
        if (this.phase.isPlayItem()) {
            StringBuilder sb = new StringBuilder();
            this.filterNode.getConditionNode().getItems().forEach(o -> sb.append(o).append(". "));
            TtsNode ttsPhase = new TtsNode();
            ttsPhase.setType("ment");
            ttsPhase.setPlay(sb.toString());
            ttsPlayItem = Optional.of(ttsPhase);
        } else {
            ttsPlayItem = Optional.empty();
        }

        List<Phase> phases = new ArrayList<>();
        phases.add(new StartNode());
        phases.add(this.phase.getTtsNode());
        ttsPlayItem.ifPresent(phases::add);
        phases.add(this.phase.getSttNode());
        phases.add(this.phase.getFilterNode());
        phases.add(new EndNode());

        ScenarioInfo subScenario = ScenarioBuilder.buildSubScenario(scenarioRunner.getId() + "_" + phase.getId(), scenarioInfo, phases);
        subScenario.setVariables(scenarioInfo.getVariables());
        subScenarioRunner = new ScenarioRunner(subScenario, scenarioRunner.getExecutor());

        subScenarioRunner.setOnScenarioDone(() -> {
            if (subScenarioRunner.isScenarioComplete() && scenarioInfo.getVariables().containsKey(target)) {
                this.procDoneSuccess();
            } else {
                proc();
            }
        });
    }

    @Override
    public void handle() {
        if (tryCount.getAndIncrement() > phase.getRetryCnt()) {
            procDoneFail("Retry count over");
            return;
        }

        subScenarioRunner.scenarioRun();
    }

    @Override
    public void proc() {
        try {
            handle();
        } catch (Exception e) {
            log.warn("({}) Err Occurs [{}]", this.scenarioRunner.getId(), phase.getId(), e);
            procDoneFail(e);
        }
    }
}
