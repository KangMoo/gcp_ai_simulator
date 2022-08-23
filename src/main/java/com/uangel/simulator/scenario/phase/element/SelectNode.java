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
@Root(name = "select")
@Getter
@Setter
public class SelectNode extends Phase {
    @Attribute(name = "play_item", required = false)
    private boolean playItem = true;
    @Attribute(name = "retry_cnt", required = false)
    private int retryCnt;
    @Element(name = "tts")
    private TtsNode ttsNode;
    @Element(name = "stt")
    private SttNode sttNode;
    @Element(name = "filter")
    private FilterNode filterNode;
}
