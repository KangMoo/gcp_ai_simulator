package simulator.scenario;

import ai.media.stt.SttConverter;
import ai.media.tts.TtsConverter;
import lombok.Data;
import simulator.scenario.phase.base.Phase;
import simulator.utils.LocalSound;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author kangmoo Heo
 */
@Data
public class ScenarioInfo implements AutoCloseable {
    private String name;
    private Map<String, Phase> phases;
    private Map<String, String> variables = new ConcurrentHashMap<>();
    private Map<String, String> savedData = new ConcurrentHashMap<>();
    private SttConverter sttConverter;
    private TtsConverter ttsConverter;
    private LocalSound localSound;

    public ScenarioInfo(SttConverter sttConverter, TtsConverter ttsConverter, LocalSound localSound, Map<String, Phase> phases) {
        this.sttConverter = sttConverter;
        this.ttsConverter = ttsConverter;
        this.localSound = localSound;
        this.phases = phases;
    }

    public Phase findPhase(String id) {
        return phases.get(id);
    }

    public void playTtsText(String msg) {
        localSound.play(getTtsConverter().convertText(msg).toByteArray());
    }

    public void playTtsSsml(String ssml) {
        localSound.play(getTtsConverter().convertSsml(ssml).toByteArray());
    }

    @Override
    public void close() {
        if (this.localSound != null) this.localSound.stop();
    }
}
