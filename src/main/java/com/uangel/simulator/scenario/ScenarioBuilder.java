package com.uangel.simulator.scenario;

import ai.media.stt.SttConverter;
import ai.media.tts.TtsConverter;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.SpeechContext;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.uangel.simulator.scenario.phase.Scenario;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import com.uangel.simulator.scenario.phase.base.Phase;
import com.uangel.simulator.utils.Kr2Num;
import com.uangel.simulator.utils.LocalSound;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kangmoo Heo
 */
public class ScenarioBuilder {

    public static ScenarioInfo buildScenarioInfo(File scenarioXml) throws Exception {
        Scenario scenario = parseScenarioFile(scenarioXml);
        Map<String, Phase> phases = new ConcurrentHashMap<>();
        Field[] fields = Scenario.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            ((List<Phase>) field.get(scenario)).forEach(o -> phases.put(o.getId(), o));
        }

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

        SttConverter sttConverter = SttConverter.newBuilder()
                .setEncoding(AudioEncoding.LINEAR16)
                .setSampleRateHertz(16000)
                .setLanguageCode("ko-KR")
                .addSpeechContexts(speechContext)
                .build();

        TtsConverter ttsConverter = TtsConverter.newBuilder()
                .setAudioEncoding(com.google.cloud.texttospeech.v1.AudioEncoding.LINEAR16)
                .setSampleRateHertz(16000)
                .setLanguageCode("ko-KR")
                .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                .build();

        LocalSound localSound = new LocalSound();
        localSound.start();

        return new ScenarioInfo(sttConverter, ttsConverter, localSound, phases);
    }

    public static Map<String, Phase> buildPhaseChain(String id, List<Phase> phases) {
        for (int i = 0; i < phases.size(); i++) {
            Phase phase = phases.get(i);
            phase.setId(id + "_" + i);
            phase.setNextPhase(id + "_" + (i + 1));
        }

        Map<String, Phase> phaseMap = new ConcurrentHashMap<>();
        phases.forEach(o -> phaseMap.put(o.getId(), o));

        return phaseMap;
    }

    public static Map<String, Phase> buildPhaseChain(String id, Phase... phases) {
        return buildPhaseChain(id, Arrays.asList(phases));
    }

    public static ScenarioInfo buildSubScenario(String id, ScenarioInfo mainScenario, List<Phase> phases) {
        return new ScenarioInfo(
                mainScenario.getSttConverter(),
                mainScenario.getTtsConverter(),
                mainScenario.getLocalSound(),
                buildPhaseChain(id, phases)
        );
    }

    public static ScenarioInfo buildSubScenario(String id, ScenarioInfo mainScenario, Phase... phases) {
        return buildSubScenario(id, mainScenario, Arrays.asList(phases));
    }

    private static Scenario parseScenarioFile(File scenarioXml) throws Exception {
        Serializer serializer = new Persister();
        return serializer.read(Scenario.class, scenarioXml);
    }
}
