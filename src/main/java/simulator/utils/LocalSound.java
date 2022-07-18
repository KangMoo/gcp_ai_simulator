package simulator.utils;

import lombok.Data;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * @author kangmoo Heo
 */
@Data
public class LocalSound {
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private TargetDataLine microphone;
    private SourceDataLine speakers;
    private AtomicBoolean isQuit = new AtomicBoolean(false);
    private Consumer<byte[]> onDataFromMike = o -> {};

    public void start(){
        AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
        try {
            microphone = AudioSystem.getTargetDataLine(format);

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            byte[] data = new byte[microphone.getBufferSize() / 5];
            microphone.start();
            AudioInputStream audio = new AudioInputStream(microphone);


            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            speakers.open(format);
            speakers.start();
            speakers.drain();
            executor.submit(() -> {
                try {
                    while (!isQuit.get()) {
                        audio.read(data);
                        out.write(data, 0, data.length);
                        onDataFromMike.accept(data);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            });

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        isQuit.set(true);
        executor.shutdown();
        speakers.drain();
        speakers.close();
        microphone.close();
    }

    public void play(byte[] data){
        this.speakers.write(data, 0, data.length);
    }

}
