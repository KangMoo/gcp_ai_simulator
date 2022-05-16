package simulator.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author kangmoo Heo
 */
@Data
public class Kr2Num {
    public static Map<String, Integer> cardinalNum = new HashMap<>();
    public static Map<String, Integer> ordinalNumUnits = new HashMap<>();
    public static Map<String, Integer> ordinalNumTens = new HashMap<>();
    public static Set<String> ordUnit = new HashSet<>();
    public static Map<String, String> han2NumMap = new HashMap<>();

    static {
        ordinalNumUnits.put("한", 1);
        ordinalNumUnits.put("두", 2);
        ordinalNumUnits.put("세", 3);
        ordinalNumUnits.put("네", 4);
        ordinalNumUnits.put("하나", 1);
        ordinalNumUnits.put("둘", 2);
        ordinalNumUnits.put("셋", 3);
        ordinalNumUnits.put("넷", 4);
        ordinalNumUnits.put("다섯", 5);
        ordinalNumUnits.put("여섯", 6);
        ordinalNumUnits.put("일곱", 7);
        ordinalNumUnits.put("여덟", 8);
        ordinalNumUnits.put("아홉", 9);

        ordinalNumTens.put("", 0);
        ordinalNumTens.put("열", 10);
        ordinalNumTens.put("스물", 20);
        ordinalNumTens.put("스무", 20);
        ordinalNumTens.put("서른", 30);
        ordinalNumTens.put("마흔", 40);
        ordinalNumTens.put("쉰", 50);
        ordinalNumTens.put("예순", 60);
        ordinalNumTens.put("일흔", 70);
        ordinalNumTens.put("여든", 80);
        ordinalNumTens.put("아흔", 90);

        ordUnit.add("개입");
        ordUnit.add("개");
        ordUnit.add("명");
        ordUnit.add("묶음");
        ordUnit.add("단");
        ordUnit.add("모");
        ordUnit.add("세트");
        ordUnit.add("병");
        ordUnit.add("장");
        ordUnit.add("박스");
        ordUnit.add("봉지");
        ordUnit.add("팩");
        ordUnit.add("줄");
        ordUnit.add("망");
        ordUnit.add("포");
        ordUnit.add("말");
        ordUnit.add("캔");
        ordUnit.add("판");
        ordUnit.add("자루");
        ordUnit.add("가마니");
        ordUnit.add("통");
        ordUnit.add("살");
        ordUnit.add("시");
        ordUnit.add("가지");

        for (String unit : ordUnit) {
            ordinalNumTens.forEach((tenHan, tenNum) -> {
                ordinalNumUnits.forEach((digitHan, digitNum) -> {
                    han2NumMap.put(tenHan + digitHan + unit, tenNum + digitNum + " " + unit);
                    han2NumMap.put(tenHan + digitHan + " " + unit, tenNum + digitNum + " " + unit);
                    han2NumMap.put(tenHan + " " + digitHan + unit, tenNum + digitNum + " " + unit);
                    han2NumMap.put(tenHan + " " + digitHan + " " + unit, tenNum + digitNum + " " + unit);
                });
            });
        }
    }
}