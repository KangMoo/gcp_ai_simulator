package com.uangel.simulator.utils;

import lombok.Data;

import java.util.*;

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
    public static Map<String, String> num2hanMap = new HashMap<>();

    static {
        ordinalNumUnits.put("하나", 1);
        ordinalNumUnits.put("둘", 2);
        ordinalNumUnits.put("셋", 3);
        ordinalNumUnits.put("넷", 4);
        ordinalNumUnits.put("다섯", 5);
        ordinalNumUnits.put("여섯", 6);
        ordinalNumUnits.put("일곱", 7);
        ordinalNumUnits.put("여덟", 8);
        ordinalNumUnits.put("아홉", 9);
        ordinalNumUnits.put("한", 1);
        ordinalNumUnits.put("두", 2);
        ordinalNumUnits.put("세", 3);
        ordinalNumUnits.put("네", 4);

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
        ordinalNumTens.put("백", 100);
        ordinalNumTens.put("천", 1000);

//        ordUnit.add("개입");
        ordUnit.add("개");
//        ordUnit.add("명");
//        ordUnit.add("묶음");
//        ordUnit.add("단");
//        ordUnit.add("모");
//        ordUnit.add("세트");
//        ordUnit.add("병");
//        ordUnit.add("장");
//        ordUnit.add("박스");
//        ordUnit.add("봉지");
//        ordUnit.add("팩");
//        ordUnit.add("줄");
//        ordUnit.add("망");
//        ordUnit.add("포");
//        ordUnit.add("말");
//        ordUnit.add("캔");
        ordUnit.add("판");
//        ordUnit.add("자루");
//        ordUnit.add("가마니");
//        ordUnit.add("통");
//        ordUnit.add("살");
//        ordUnit.add("시");
//        ordUnit.add("가지");

        for (String unit : ordUnit) {
            ordinalNumTens.forEach((tenHan, tenNum) -> {
                ordinalNumUnits.forEach((digitHan, digitNum) -> {
                    han2NumMap.put(tenHan + digitHan + unit, tenNum + digitNum + " " + unit);
                    han2NumMap.put(tenHan + digitHan + " " + unit, tenNum + digitNum + " " + unit);
                    han2NumMap.put(tenHan + " " + digitHan + unit, tenNum + digitNum + " " + unit);
                    han2NumMap.put(tenHan + " " + digitHan + " " + unit, tenNum + digitNum + " " + unit);
                });
            });

            han2NumMap.put("하나", "1");
            han2NumMap.put("둘", "2");
            han2NumMap.put("셋", "3");
            han2NumMap.put("넷", "4");
            han2NumMap.put("다섯", "5");
            han2NumMap.put("여섯", "6");
            han2NumMap.put("일곱", "7");
            han2NumMap.put("여덟", "8");
            han2NumMap.put("아홉", "9");
            han2NumMap.put("열", "10");
            han2NumMap.put("스물", "20");
            han2NumMap.put("서른", "30");
            han2NumMap.put("마흔", "40");
            han2NumMap.put("쉰", "50");
            han2NumMap.put("예순", "60");
            han2NumMap.put("일흔", "70");
            han2NumMap.put("여든", "80");
            han2NumMap.put("아흔", "90");
            han2NumMap.put("백", "100");

            List<String> hanDigit = new ArrayList<>();
            hanDigit.add("하나");
            hanDigit.add("둘");
            hanDigit.add("셋");
            hanDigit.add("넷");
            hanDigit.add("다섯");
            hanDigit.add("여섯");
            hanDigit.add("일곱");
            hanDigit.add("여덟");
            hanDigit.add("아홉");
            List<String> tenDigit = new ArrayList<>();
            tenDigit.add("열");
            tenDigit.add("스물");
            tenDigit.add("서른");
            tenDigit.add("마흔");
            tenDigit.add("쉰");
            tenDigit.add("예순");
            tenDigit.add("일흔");
            tenDigit.add("여든");
            tenDigit.add("아흔");
            tenDigit.add("백");
            for (int i = 1; i < 100; i++) {

                num2hanMap.put(Integer.toString(i), (i / 10 == 0 ? "" : tenDigit.get(i / 10 - 1)) + " " + (i % 10 == 0 ? "" : hanDigit.get(i % 10 - 1)));
            }
        }
    }
}