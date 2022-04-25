package simulator.scenario.phase;

import lombok.Getter;
import lombok.ToString;
import simulator.scenario.Scenario;
import simulator.scenario.phase.base.Phase;

import javax.script.ScriptException;

/**
 * @author kangmoo Heo
 */
@Getter
@ToString
public class TtsPhase extends Phase {
    private final String value;

    public TtsPhase(Scenario scenario, String value) {
        super(scenario);
        this.value = value;
    }

    @Override
    public void run() throws ScriptException {
        System.out.println("TTS : " + scenario.getEngine().eval("`" + value + "`"));
        byte[] result = scenario.getTtsConverter().convertText((String)(scenario.getEngine().eval("`" + value + "`"))).toByteArray();
        scenario.getLocalSound().getSpeakers().write(result, 0, result.length);
        // Just TTS
    }
}
