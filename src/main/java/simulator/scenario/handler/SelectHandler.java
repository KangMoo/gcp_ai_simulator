package simulator.scenario.handler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import simulator.scenario.ScenarioRunner;
import simulator.scenario.handler.base.PhaseHandler;
import simulator.scenario.phase.base.Phase;
import simulator.scenario.phase.element.SelectNode;

/**
 * @author kangmoo Heo
 */
@Slf4j
@Getter
@Setter
public class SelectHandler extends PhaseHandler<SelectNode> {

    private boolean success;
    private int tryCnt = 0;

    private TtsHandler ttsHandler;
    private SttHandler sttHandler;
    private FilterHandler filterHandler;

    public SelectHandler(ScenarioRunner scenarioRunner) {
        super(scenarioRunner);
        this.ttsHandler = new TtsHandler(scenarioRunner);
        this.ttsHandler.setPhase(phase.getTtsNode());

        this.sttHandler = new SttHandler(scenarioRunner);
        this.sttHandler.setPhase(phase.getSttNode());

        this.filterHandler = new FilterHandler(scenarioRunner);
        this.filterHandler.setPhase(phase.getFilterNode());
    }

    @Override
    public void handle() throws Exception {
        while (tryCnt++ <= phase.getRetryCnt() && !success) {
            this.ttsHandler.handle();

            if (phase.isPlayItem()) {
                StringBuilder sb = new StringBuilder();
                phase.getFilterNode().getConditionNode().getItems().forEach(o -> sb.append(o).append(". "));
                log.info("TTS : {}", sb);
                scenarioInfo.playTtsText(sb.toString());
            }

            this.sttHandler.handle();
            this.filterHandler.handle();
            this.success = filterHandler.isSuccess();
        }
    }

    @Override
    public Phase getNextPhase() {
        return success ?
                scenarioInfo.findPhase(phase.getNextPhase()) :
                null;
    }
}
