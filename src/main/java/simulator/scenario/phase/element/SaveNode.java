package simulator.scenario.phase.element;

import lombok.Getter;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import simulator.scenario.phase.base.Phase;

import java.util.List;

/**
 * @author kangmoo Heo
 */
@Root(name = "save")
@Getter
public class SaveNode extends Phase {
    @ElementList(entry = "variable", inline = true)
    private List<String> variables;
}
