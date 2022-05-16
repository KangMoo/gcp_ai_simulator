package simulator;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import simulator.scenario.ScenarioRunner;

/**
 * @author kangmoo Heo
 */
public class SimMain {

    public static void main(String[] args) {
        // GOOGLE_APPLICATION_CREDENTIALS=/Users/heokangmoo/.key/esoteric-cider-346402-d9eba3f11e9d.json
        ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("io.grpc.netty").setLevel(Level.INFO);
        new ScenarioRunner("scenario2.xml").run();
    }
}