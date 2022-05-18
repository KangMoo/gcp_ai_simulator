package simulator.scenario;

import ai.media.stt.SttConverter;
import ai.media.tts.TtsConverter;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.SpeechContext;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import lombok.Data;
import simulator.scenario.phase.base.Phase;
import simulator.utils.Kr2Num;
import simulator.utils.LocalSound;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author kangmoo Heo
 */
@Data
public class Scenario {
    private String name;
    private List<Phase> phases = new ArrayList<>();
    private AtomicInteger currentPos = new AtomicInteger();
    private ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
    private SttConverter sttConverter;
    // private SttConverter sttDigitConverter;
    private TtsConverter ttsConverter;
    private LocalSound localSound = new LocalSound();

    public Scenario() {
        SpeechContext.Builder builder = SpeechContext.newBuilder()
                .addPhrases("고구마").setBoost(50.0F)
                .addPhrases("페퍼로니").setBoost(50.0F)
                .addPhrases("포테이토").setBoost(50.0F)
                .addPhrases("레귤러").setBoost(50.0F)
                .addPhrases("라지").setBoost(50.0F)
                .addPhrases("오리진").setBoost(50.0F)
                .addPhrases("골드").setBoost(50.0F)
                .addPhrases("크림치즈").setBoost(50.0F)
                .addPhrases("에그타르트").setBoost(50.0F)
                .addPhrases("치즈캡").setBoost(50.0F)
                .addPhrases("피자").setBoost(50.0F)
                .addPhrases("사이즈").setBoost(50.0F)
                .addPhrases("엣지").setBoost(50.0F)
                .addPhrases("네").setBoost(50.0F)
                .addPhrases("예").setBoost(50.0F)
                .addPhrases("아니오").setBoost(50.0F)
                .addPhrases("콜라").setBoost(50.0F)
                .addPhrases("사이다").setBoost(50.0F);
        Kr2Num.han2NumMap.keySet().forEach(o -> builder.addPhrases(o).setBoost(20.0F));
        SpeechContext speechContext = builder.build();

        sttConverter = SttConverter.newBuilder()
                .setEncoding(AudioEncoding.LINEAR16)
                .setSampleRateHertz(16000)
                .setLanguageCode("ko-KR")
                .addSpeechContexts(speechContext)
                .build();

//        sttDigitConverter = SttConverter.newBuilder()
//                .setEncoding(AudioEncoding.LINEAR16)
//                .setSampleRateHertz(16000)
//                .setLanguageCode("ko-KR")
//                .addSpeechContexts(SpeechContext.newBuilder().addPhrases("$OOV_CLASS_ALPHANUMERIC_SEQUENCE").build())
//                .build();

        ttsConverter = TtsConverter.newBuilder()
                .setAudioEncoding(com.google.cloud.texttospeech.v1.AudioEncoding.LINEAR16)
                .setSampleRateHertz(16000)
                .setLanguageCode("ko-KR")
                .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                .build();

        localSound.start();
    }

    public Phase getCurrentPhase() {
        int pos = currentPos.getAndIncrement();
        if (pos >= phases.size()) return null;
        return phases.get(pos);
    }
}
