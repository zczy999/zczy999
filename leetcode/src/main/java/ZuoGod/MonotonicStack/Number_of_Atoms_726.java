package ZuoGod.MonotonicStack;

import java.util.*;

public class Number_of_Atoms_726 {

    int where;

    public String countOfAtoms(String formula) {
        Map<String, Integer> map = f(formula.toCharArray(), 0);
        StringBuilder ans = new StringBuilder();
        for (String key : map.keySet()) {
            ans.append(key);
            int cnt = map.get(key);
            if (cnt > 1) {
                ans.append(cnt);
            }
        }
        return ans.toString();
    }

    public Map<String, Integer> f(char[] formula, int i) {
        Map<String, Integer> atomCountMap = new TreeMap<>();
        Map<String, Integer> subMap = null;
        Integer num = 0;
        String atom = "";

        while (i < formula.length && formula[i] != ')') {
            if (Character.isDigit(formula[i])) {
                num = num * 10 + formula[i] - '0';
                i++;
            } else if (formula[i] != '(') {
                if (Character.isUpperCase(formula[i])) {
                    fill(atom, num, atomCountMap, subMap);
                    subMap = null;
                    atom = "";
                    num = 0;
                }
                atom += formula[i];
                i++;
            } else {
                // **进入新括号前先结算手头的 pending 对象**
                fill(atom, num, atomCountMap, subMap);
                subMap = null;
                atom = "";
                num = 0;
                subMap = f(formula, i + 1);
                i = where + 1;
            }
        }
        where = i;
        fill(atom, num, atomCountMap, subMap);
        return atomCountMap;
    }

    private static void fill(String atom, Integer num, Map<String, Integer> atomCountMap, Map<String, Integer> subMap) {
        if (atom.length() != 0) {
            addAtom(num, atomCountMap, atom);
        } else if (subMap != null) {
            addAtomMap(subMap, atomCountMap, num);
        }
    }

    private static void addAtomMap(Map<String, Integer> subMap, Map<String, Integer> atomCountMap, int num) {
        int mul = (num == 0 ? 1 : num); // **省略倍数视为 1**
        for (Map.Entry<String, Integer> entry : subMap.entrySet()) {
            atomCountMap.put(entry.getKey(), atomCountMap.getOrDefault(entry.getKey(), 0) + entry.getValue() * mul);
        }
    }

    private static void addAtom(int num, Map<String, Integer> atomCountMap, String atom) {
        if (num == 0) {
            atomCountMap.put(atom, atomCountMap.getOrDefault(atom, 0) + 1);
        } else {
            atomCountMap.put(atom, atomCountMap.getOrDefault(atom, 0) + num);
        }
    }


}
