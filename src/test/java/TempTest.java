
import org.junit.Test;
import simulator.scenario.Scenario;
import simulator.scenario.ScenarioBuilder;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;

/**
 * @author kangmoo Heo
 */
public class TempTest {
    @Test
    public void test() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        Integer result = (Integer)engine.eval("var i = 0; i");
        try {
            Integer result2 = (Integer)engine.eval("++iasdf");
            System.out.println(result2);
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            Integer result3 = (Integer)engine.eval("++i");
            System.out.println(result3);
        } catch (Exception e){
            e.printStackTrace();
        }


        System.out.println(result);
    }

    @Test
    public void test2(){
        try {
            Scenario scenario = ScenarioBuilder.buildScenario(new File("/Users/heokangmoo/Downloads/aisim/scenario.xml"));
            System.out.println(scenario);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
