package simulator.scenario;

import org.slf4j.Logger;
import simulator.scenario.phase.base.Phase;

import java.io.File;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author kangmoo Heo
 */
public class ScenarioRunner {
    private static final Logger log = getLogger(ScenarioRunner.class);

    public void run(String xmlFile) {
        Scenario scenario;
        try {
            scenario = ScenarioBuilder.buildScenario(new File(xmlFile));
        } catch (Exception e) {
            log.error("Fail to build Scenario", e);
            return;
        }

        while(true){
            Phase nowPhase = scenario.getCurrentPhase();
            if(nowPhase == null) break;
            try {
                nowPhase.run();
            } catch (Exception e){
                log.warn("Err Occurs while running phase [{}]", nowPhase, e);
                break;
            }
        }
        scenario.getLocalSound().stop();
    }
}
