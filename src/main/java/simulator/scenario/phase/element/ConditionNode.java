package simulator.scenario.phase.element;

import lombok.Getter;
import lombok.Setter;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import simulator.scenario.phase.base.Phase;

import java.util.List;

/**
 * @author kangmoo Heo
 */
@Root(name = "condition")
@Getter
@Setter
public class ConditionNode extends Phase {
    @Attribute(name = "type")
    private String type;
    @ElementList(entry = "item", required = false, inline = true)
    private List<String> items;
}
