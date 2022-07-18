package simulator.scenario.handler;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import simulator.scenario.ScenarioInfo;
import simulator.scenario.handler.base.PhaseHandler;
import simulator.scenario.phase.base.Phase;
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
@RequiredArgsConstructor
@Data
public class TtsHandler implements PhaseHandler {
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{([^\\${}]+)\\}");
    @NonNull
    private ScenarioInfo scenarioInfo;
    @NonNull
    private TtsNode phase;

    @Override
    public void handle() throws Exception {
        switch (phase.getType()) {
            case "ment":
                String ttsText = phase.getPlay();
                Matcher matcher = VARIABLE_PATTERN.matcher(ttsText);
                while (matcher.find()) {
                    ttsText = matcher.replaceFirst(scenarioInfo.getVariables().get(matcher.group(1)));
                    matcher = VARIABLE_PATTERN.matcher(ttsText);
                }
                log.info("TTS : {}", ttsText);
                scenarioInfo.playTtsText(ttsText);
                break;
            case "file":
                log.info("File play : {}", phase.getPlay());
                scenarioInfo.getLocalSound().play(Files.readAllBytes(Path.of(new File(phase.getPlay()).getAbsolutePath())));
                break;
            default:
                throw new NullPointerException("TTS Type is Unknown [" + phase.getType() + "]");
        }
    }

    @Override
    public Phase getNextPhase() {
        return scenarioInfo.findPhase(phase.getNextPhase());
    }
}
