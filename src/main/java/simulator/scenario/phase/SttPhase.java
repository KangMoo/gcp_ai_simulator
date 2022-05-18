package simulator.scenario.phase;

import ai.media.stt.SttConverter;
import lombok.Getter;
import lombok.ToString;
import simulator.scenario.Scenario;
import simulator.scenario.phase.base.Phase;
import simulator.utils.Kr2Num;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author kangmoo Heo
 */
@Getter
@ToString
public class SttPhase extends Phase {
    private final String value;
    private final long duration;
    private final boolean digit;

    public SttPhase(Scenario scenario, String value, long duration, boolean digit) {
        super(scenario);
        this.value = value;
        this.duration = duration;
        this.digit = digit;
    }

    @Override
    public void run() throws Exception {
        SttConverter sttConverter = scenario.getSttConverter();

        sttConverter.start();
        scenario.getLocalSound().setOnDataFromMike(sttConverter::inputData);
        Thread.sleep(duration + 100);
        scenario.getLocalSound().setOnDataFromMike(o -> {});
        sttConverter.stop();

        String result = Optional.ofNullable(sttConverter.getResultTexts()).filter(o -> !o.isEmpty()).map(o -> o.get(o.size() - 1)).orElse(null);

        AtomicReference<String> finalResult = new AtomicReference<>(result);
        Kr2Num.han2NumMap.keySet().forEach(o -> {
            if(Kr2Num.han2NumMap.get(o) != null){
                finalResult.set(finalResult.get().replace(o, Kr2Num.han2NumMap.get(o)));
            }
        });
        result = finalResult.get();

        System.out.println("STT : " + result);
        if (result != null) scenario.getEngine().eval("var " + value + "=\"" + result + "\";");
    }
}
