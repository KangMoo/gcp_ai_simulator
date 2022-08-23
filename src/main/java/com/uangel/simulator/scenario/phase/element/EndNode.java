package com.uangel.simulator.scenario.phase.element;

import lombok.Getter;
import lombok.Setter;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import com.uangel.simulator.scenario.phase.base.Phase;

/**
 * @author kangmoo Heo
 */
@Root(name = "end")
@Getter
@Setter
public class EndNode extends Phase {
    @Element(name = "save", required = false)
    private SaveNode saveNode;
}
