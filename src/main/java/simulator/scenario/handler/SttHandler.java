package simulator.scenario.handler;

import ai.media.stt.SttConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import simulator.scenario.ScenarioRunner;
import simulator.scenario.handler.base.PhaseHandler;
import simulator.scenario.phase.element.SttNode;
import simulator.utils.Kr2Num;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author kangmoo Heo
 */
@Slf4j
@Getter
@Setter
public class SttHandler extends PhaseHandler<SttNode> {

    private AtomicInteger tryCount = new AtomicInteger();
    private String result;

    public SttHandler(ScenarioRunner scenarioRunner) {
        super(scenarioRunner);
    }

    public void handle() {
        SttConverter sttConverter = scenarioInfo.getSttConverter();

        if (tryCount.getAndIncrement() > phase.getRetryCnt()) {
            procDoneFail("Retry count over");
            return;
        }

        startStt();
        executor.schedule(() -> {
            stopStt();

            Optional.ofNullable(sttConverter.getResultTexts()).filter(o -> !o.isEmpty()).map(o -> o.get(o.size() - 1))
                    .ifPresentOrElse(sttText -> {
                        AtomicReference<String> finalResult = new AtomicReference<>(sttText);
                        Kr2Num.han2NumMap.keySet().forEach(o -> {
                            if (Kr2Num.han2NumMap.get(o) != null) {
                                finalResult.set(finalResult.get().replace(o, Kr2Num.han2NumMap.get(o)));
                            }
                        });
                        sttText = finalResult.get();
                        scenarioInfo.getVariables().put(phase.getVariable(), sttText);
                        log.info("STT : {}", sttText);

                        procDoneSuccess();
                    }, () -> executor.submit(() -> {
                        try {
                            handle();
                        } catch (Exception e) {
                            log.warn("ERR Occurs while Handling STT Phase", e);
                            procDoneFail(e);
                        }
                    }));
        }, phase.getDuration() * 1000L + 100L, TimeUnit.MILLISECONDS);
    }

    @Override
    public void proc() {
        try {
            handle();
        } catch (Exception e) {
            log.warn("{} Err Occurs", phase.getId(), e);
            procDoneFail(e);
        }
    }

    public void startStt() {
        scenarioInfo.getSttConverter().start();
        scenarioInfo.getLocalSound().onInputAction(scenarioInfo.getSttConverter()::inputData);
        log.debug("STT START");
    }

    public void stopStt() {
        scenarioInfo.getLocalSound().onInputAction(o -> {
        });
        scenarioInfo.getSttConverter().stop();
        log.debug("STT STOP");
    }

}
