package simulator.scenario.phase.element;

import lombok.Getter;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import simulator.scenario.phase.base.Phase;

/**
 * @author kangmoo Heo
 */
@Root(name = "end")
@Getter
public class EndNode extends Phase {
    @Element(name = "save", required = false)
    private SaveNode saveNode;
}
