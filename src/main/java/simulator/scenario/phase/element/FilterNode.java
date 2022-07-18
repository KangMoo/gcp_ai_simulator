package simulator.scenario.phase.element;

import lombok.Getter;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import simulator.scenario.phase.base.Phase;

/**
 * @author kangmoo Heo
 */
@Root(name = "filter")
@Getter
public class FilterNode extends Phase {
    @Element(name = "target")
    private String target;
    @Element(name = "variable")
    private String variable;
    @Element(name = "condition")
    private ConditionNode conditionNode;
    @Element(name = "success_goto", required = false)
    private String nextPhaseIfSuccess;
    @Element(name = "fail_goto", required = false)
    private String nextPhaseIfFail;
}
