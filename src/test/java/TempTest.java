import org.junit.Test;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * @author kangmoo Heo
 */
public class TempTest {
    @Test
    public void test() {
        Map<String, BlockingQueue<String>> sessions = new ConcurrentHashMap<>();
        for (int i = 0; i < 3; i++) {
            sessions.put("" + i, new ArrayBlockingQueue<>(32));
        }


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                sessions.get("" + i).add("" + i + ":" + +j);
            }
        }

        System.out.println(sessions);
        sessions.values().stream().flatMap(o ->{
            Stream<String> res = o.stream();
            o.clear();
            return res;
        }).forEach(System.out::println);
    }
}
