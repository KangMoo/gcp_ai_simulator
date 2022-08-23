package com.uangel.simulator.scenario.phase;

import com.uangel.simulator.scenario.phase.element.*;
import lombok.Data;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * @author kangmoo Heo
 */
@Root(name = "scenario")
@Data
public class Scenario {
    @ElementList(name = "start", inline = true)
    private List<StartNode> startNode;
    @ElementList(name = "end", inline = true)
    private List<EndNode> endNode;
    @ElementList(name = "stt", required = false, inline = true)
    private List<SttNode> sttNodes;
    @ElementList(name = "tts", required = false, inline = true)
    private List<TtsNode> ttsNodes;
    @ElementList(name = "filter", required = false, inline = true)
    private List<FilterNode> filterNodes;
    @ElementList(name = "select", required = false, inline = true)
    private List<SelectNode> selectNodes;
    @ElementList(name = "extract", required = false, inline = true)
    private List<ExtractNode> extractNodes;
}
