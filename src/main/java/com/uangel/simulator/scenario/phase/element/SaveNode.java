package com.uangel.simulator.scenario.phase.element;

import lombok.Getter;
import lombok.Setter;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import com.uangel.simulator.scenario.phase.base.Phase;

import java.util.List;

/**
 * @author kangmoo Heo
 */
@Root(name = "save")
@Getter
@Setter
public class SaveNode extends Phase {
    @ElementList(entry = "variable", inline = true)
    private List<String> variables;
}
