package simulator.scenario.phase.element;

import lombok.Getter;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import simulator.scenario.phase.base.Phase;

/**
 * @author kangmoo Heo
 */
@Root(name = "tts")
@Getter
public class TtsNode extends Phase {
    @Attribute(name = "type")
    private String type;
    @Element(name = "play")
    private String play;
}
