package simulator.scenario.handler;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import simulator.scenario.ScenarioInfo;
import simulator.scenario.handler.base.PhaseHandler;
import simulator.scenario.phase.base.Phase;
import simulator.scenario.phase.element.SelectNode;

import java.util.logging.Filter;

/**
 * @author kangmoo Heo
 */
@Slf4j
@Data
public class SelectHandler implements PhaseHandler {
    @NonNull
    private ScenarioInfo scenarioInfo;
    @NonNull
    private SelectNode phase;

    private boolean success;
    private int tryCnt = 0;

    private TtsHandler ttsHandler;
    private SttHandler sttHandler;
    private FilterHandler filterHandler;

    public SelectHandler(@NonNull ScenarioInfo scenarioInfo, @NonNull SelectNode phase) {
        this.scenarioInfo = scenarioInfo;
        this.phase = phase;
        this.ttsHandler = new TtsHandler(scenarioInfo, phase.getTtsNode());
        this.sttHandler = new SttHandler(scenarioInfo, phase.getSttNode());
        this.filterHandler = new FilterHandler(scenarioInfo, phase.getFilterNode());
    }

    @Override
    public void handle() throws Exception {
        while(tryCnt++ <= phase.getRetryCnt() && !success){
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
