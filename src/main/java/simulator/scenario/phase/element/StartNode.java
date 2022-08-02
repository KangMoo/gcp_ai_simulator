package simulator.scenario.phase.element;

import lombok.Getter;
import lombok.Setter;
import org.simpleframework.xml.Root;
import simulator.scenario.phase.base.Phase;

/**
 * @author kangmoo Heo
 */
@Root(name = "start")
@Getter
@Setter
public class StartNode extends Phase {
}
