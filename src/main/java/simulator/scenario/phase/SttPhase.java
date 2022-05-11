package simulator.scenario.phase;

import ai.media.stt.SttConverter;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.SpeechContext;
import lombok.Getter;
import lombok.ToString;
import simulator.scenario.Scenario;
import simulator.scenario.phase.base.Phase;
import simulator.utils.Han2Num;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


        if (digit) {
            SttConverter sttConverter = scenario.getSttConverter();
            sttConverter.start();
            scenario.getLocalSound().setOnDataFromMike(sttConverter::inputData);
            Thread.sleep(duration + 100);
            scenario.getLocalSound().setOnDataFromMike(o -> {
            });
            sttConverter.stop();

            String result = Optional.ofNullable(sttConverter.getResultTexts()).filter(o -> !o.isEmpty()).map(o -> o.get(o.size() - 1)).orElse(null);
            System.out.println("STT : " + result);
            if (result == null) return;
            AtomicReference<String> finalResult = new AtomicReference<>(result);
            Han2Num.han2NumMap.keySet().forEach(o -> finalResult.set(finalResult.get().replaceAll(o, Integer.toString(Han2Num.han2NumMap.get(o)))));
            result = finalResult.get();
            Matcher matcher = Pattern.compile("\\d+").matcher(result);
            result = matcher.find() ? matcher.group() : "-1";
            if (result.equals("-1")) return;
            scenario.getEngine().eval("var " + value + "=" + result + ";");
        } else {
            SttConverter sttConverter = scenario.getSttConverter();
            sttConverter.start();
            scenario.getLocalSound().setOnDataFromMike(sttConverter::inputData);
            Thread.sleep(duration + 100);
            scenario.getLocalSound().setOnDataFromMike(o -> { });
            sttConverter.stop();

            String result = Optional.ofNullable(sttConverter.getResultTexts()).filter(o -> !o.isEmpty()).map(o -> o.get(o.size() - 1)).orElse(null);
            System.out.println("STT : " + result);
            if (result != null) scenario.getEngine().eval("var " + value + "=\"" + result + "\";");
        }
    }
}
