package simulator;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import simulator.scenario.ScenarioBuilder;
import simulator.scenario.ScenarioInfo;
import simulator.scenario.ScenarioRunner;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author kangmoo Heo
 */
public class SimMain {

    public static void main(String[] args) throws Exception {
        // GOOGLE_APPLICATION_CREDENTIALS=/Users/heokangmoo/.key/dulcet-metric-356107-415b9145a504.json
        ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("io.grpc.netty").setLevel(Level.INFO);
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        try (ScenarioInfo scenarioInfo = ScenarioBuilder.buildScenarioInfo(new File("scenario.xml"))) {
            Future<ScenarioInfo> result = new ScenarioRunner(scenarioInfo, executor).scenarioRun();

            StringBuilder sb = new StringBuilder("결과 : ");
            result.get().getSavedData().forEach((k, v) -> sb.append("[").append(k).append(" : ").append(v).append("] "));
            System.out.println(sb);
        } finally {
            executor.shutdown();
        }
    }
}