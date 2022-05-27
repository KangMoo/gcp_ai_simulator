package simulator.scenario.phase;

import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import simulator.scenario.Scenario;
import simulator.scenario.phase.base.Phase;

import javax.script.ScriptException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author kangmoo Heo
 */
@Getter
@ToString
public class TtsPhase extends Phase {
    private static final Logger log = getLogger(TtsPhase.class);
    private final String value;

    public TtsPhase(Scenario scenario, String value) {
        super(scenario);
        this.value = value;
    }

    @Override
    public void run() throws ScriptException {
        log.debug("TTS : {}", scenario.getEngine().eval("`" + value + "`"));
        byte[] result = scenario.getTtsConverter().convertText((String) (scenario.getEngine().eval("`" + value + "`"))).toByteArray();
        scenario.getLocalSound().getSpeakers().write(result, 0, result.length);
        // Just TTS
    }
}
