package simulator.scenario.handler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import simulator.scenario.ScenarioRunner;
import simulator.scenario.handler.base.PhaseHandler;
import simulator.scenario.phase.element.TtsNode;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kangmoo Heo
 */
@Slf4j
@Getter
@Setter
public class TtsHandler extends PhaseHandler<TtsNode> {
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{([^\\${}]+)\\}");

    public TtsHandler(ScenarioRunner scenarioRunner) {
        super(scenarioRunner);
    }

    @Override
    public void handle() {
        try {
            switch (phase.getType()) {
                case "ment":
                    String ttsText = phase.getPlay();
                    Matcher matcher = VARIABLE_PATTERN.matcher(ttsText);
                    while (matcher.find()) {
                        ttsText = matcher.replaceFirst(scenarioInfo.getVariables().get(matcher.group(1)));
                        matcher = VARIABLE_PATTERN.matcher(ttsText);
                    }
                    log.info("({}) TTS : {}", this.scenarioRunner.getId(), ttsText);
                    scenarioInfo.playTtsText(ttsText);
                    break;
                case "file":
                    log.info("({}) File play : {}", this.scenarioRunner.getId(), phase.getPlay());
                    scenarioInfo.getLocalSound().play(Files.readAllBytes(Path.of(new File(phase.getPlay()).getAbsolutePath())));
                    break;
                default:
                    throw new NullPointerException("TTS Type is Unknown [" + phase.getType() + "]");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
