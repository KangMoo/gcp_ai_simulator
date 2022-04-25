package simulator.scenario;

import ai.media.stt.SttConverter;
import ai.media.tts.TtsConverter;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.SpeechContext;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import lombok.Data;
import simulator.scenario.phase.base.Phase;
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
    private SttConverter sttDigitConverter;
    private TtsConverter ttsConverter;
    private LocalSound localSound = new LocalSound();

    public Scenario() {
        sttConverter = SttConverter.newBuilder()
                .setEncoding(AudioEncoding.LINEAR16)
                .setSampleRateHertz(16000)
                .setLanguageCode("ko-KR")
                .build();

        sttDigitConverter = SttConverter.newBuilder()
                .setEncoding(AudioEncoding.LINEAR16)
                .setSampleRateHertz(16000)
                .setLanguageCode("ko-KR")
                .addSpeechContexts(SpeechContext.newBuilder().addPhrases("$OOV_CLASS_ALPHANUMERIC_SEQUENCE").build())
                .build();

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
