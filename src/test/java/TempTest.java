import lombok.extern.slf4j.Slf4j;
import simulator.utils.LocalSound;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author kangmoo Heo
 */
@Slf4j
public class TempTest {
    public static void main(String[] args) {
        LocalSound localSound = new LocalSound();
        localSound.start();
        try {
            localSound.play(Files.readAllBytes(Path.of(new File("hello.pcm").getAbsolutePath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
