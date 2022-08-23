package com.uangel.simulator.scenario.phase.element;

import lombok.Getter;
import lombok.Setter;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import com.uangel.simulator.scenario.phase.base.Phase;

/**
 * @author kangmoo Heo
 */
@Root(name = "extract")
@Getter
@Setter
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
