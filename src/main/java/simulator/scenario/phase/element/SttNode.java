package simulator.scenario.phase.element;

import lombok.Getter;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import simulator.scenario.phase.base.Phase;

/**
 * @author kangmoo Heo
 */
@Root(name = "stt")
@Getter
public class SttNode extends Phase {
    @Attribute(name = "duration", required = false)
    private int duration = 5;
    @Attribute(name = "retry_cnt", required = false)
    private int retryCnt;
    @Element(name = "variable")
    private String variable;
}
