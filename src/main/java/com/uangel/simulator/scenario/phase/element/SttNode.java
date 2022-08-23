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
@Root(name = "stt")
@Getter
@Setter
public class SttNode extends Phase {
    @Attribute(name = "duration", required = false)
    private int duration = 5;
    @Attribute(name = "retry_cnt", required = false)
    private int retryCnt;
    @Element(name = "variable")
    private String variable;
}
