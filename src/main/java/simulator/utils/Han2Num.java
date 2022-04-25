package simulator.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kangmoo Heo
 */
@Data
public class Han2Num {
    public static Map<String, Integer> han2NumMap = new HashMap<>();

    static {
        han2NumMap.put("하나", 1);
        han2NumMap.put("한", 1);
        han2NumMap.put("둘", 2);
        han2NumMap.put("두", 2);
        han2NumMap.put("셋", 3);
        han2NumMap.put("세", 3);
        han2NumMap.put("넷", 4);
        han2NumMap.put("네", 4);
        han2NumMap.put("다섯", 5);
        han2NumMap.put("여섯", 6);
        han2NumMap.put("일곱", 7);
        han2NumMap.put("여덟", 8);
        han2NumMap.put("아홉", 9);
        han2NumMap.put("열", 10);
    }
}