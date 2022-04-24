package simulator.scenario.phase;

import lombok.Getter;
import lombok.ToString;
import simulator.scenario.Scenario;
import simulator.scenario.phase.base.Phase;

import java.util.Scanner;

/**
 * @author kangmoo Heo
 */
@Getter
@ToString
public class SttPhase extends Phase {
    private final String value;
    private final long duration;
    private final boolean digit;

    public SttPhase(Scenario scenario, String value, long duration, boolean digit) {
        super(scenario);
        this.value = value;
        this.duration = duration;
        this.digit = digit;
    }

    @Override
    public void run() throws Exception {
        // STT
        Scanner scanner = new Scanner(System.in);
        System.out.print("STT : ");
        String in = scanner.nextLine();

        if (digit) {
            in = in.replaceAll("[^0-9]", "");
        }
        scenario.getEngine().eval("var " + value + "=\"" + in + "\";");
    }
}
