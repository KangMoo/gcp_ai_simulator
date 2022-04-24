package simulator.scenario;

import lombok.Data;
import simulator.scenario.phase.base.Phase;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author kangmoo Heo
 */
@Data
public class Scenario {
    private String name;
    private List<Phase> phases = new ArrayList<>();
    private AtomicInteger currentPos = new AtomicInteger();
    private ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

    public Phase getCurrentPhase(){
        int pos = currentPos.getAndIncrement();
        if(pos >= phases.size()) return null;
        return phases.get(pos);
    }
}
