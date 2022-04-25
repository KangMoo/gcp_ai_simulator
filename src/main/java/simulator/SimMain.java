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
        ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("io.grpc.netty").setLevel(Level.INFO);
        new ScenarioRunner().run("/Users/heokangmoo/Downloads/aisim/scenario.xml");
    }
}