package simulator.scenario.handler;

import ai.media.stt.SttConverter;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import simulator.scenario.ScenarioInfo;
import simulator.scenario.handler.base.PhaseHandler;
import simulator.scenario.phase.base.Phase;
import simulator.scenario.phase.element.SttNode;
import simulator.utils.Kr2Num;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author kangmoo Heo
 */
@Slf4j
@RequiredArgsConstructor
@Data
public class SttHandler implements PhaseHandler {
    @NonNull
    private ScenarioInfo scenarioInfo;
    @NonNull
    private SttNode phase;

    @Override
    public void handle() throws Exception {
        SttConverter sttConverter = scenarioInfo.getSttConverter();
        int tryCount = 0;
        String result = null;
        while (tryCount++ <= phase.getRetryCnt() && result == null) {

            // TODO 병목포인트 추후 제거!
            sttConverter.start();
            scenarioInfo.getLocalSound().setOnDataFromMike(sttConverter::inputData);
            Thread.sleep(phase.getDuration() * 1000L + 100L);
            scenarioInfo.getLocalSound().setOnDataFromMike(o -> {});
            sttConverter.stop();
            result = Optional.ofNullable(sttConverter.getResultTexts()).filter(o -> !o.isEmpty()).map(o -> o.get(o.size() - 1)).orElse(null);
            if (result == null) continue;
            AtomicReference<String> finalResult = new AtomicReference<>(result);
            Kr2Num.han2NumMap.keySet().forEach(o -> {
                if (Kr2Num.han2NumMap.get(o) != null) {
                    finalResult.set(finalResult.get().replace(o, Kr2Num.han2NumMap.get(o)));
                }
            });
            result = finalResult.get();
            scenarioInfo.getVariables().put(phase.getVariable(), result);
            log.info("STT : {}", result);
        }

        if (result == null) {
            throw new NullPointerException("STT Result is null");
        }

    }

    @Override
    public Phase getNextPhase() {
        return scenarioInfo.findPhase(phase.getNextPhase());
    }
}
