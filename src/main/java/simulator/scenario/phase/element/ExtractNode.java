package simulator.scenario.phase.element;

import lombok.Getter;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import simulator.scenario.phase.base.Phase;

/**
 * @author kangmoo Heo
 */
@Root(name = "extract")
@Getter
public class ExtractNode extends Phase {
    @Attribute(name = "type")
    String type;
    @Element(name = "target")
    private String target;
    @Element(name = "variable")
    private String variable;
    @Element(name = "success_goto", required = false)
    private String nextPhaseIfSuccess;
    @Element(name = "fail_goto", required = false)
    private String nextPhaseIfFail;
}
