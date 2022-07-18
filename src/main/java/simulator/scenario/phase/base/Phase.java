package simulator.scenario.phase.base;

import lombok.Getter;
import lombok.ToString;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * @author kangmoo Heo
 */
@Getter
@ToString
public abstract class Phase {
    @Attribute(name = "id", required = false)
    protected String id;
    @Element(name = "goto", required = false)
    protected String nextPhase;
}
