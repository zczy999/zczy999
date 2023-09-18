package leetcode;

import java.util.HashMap;
import java.util.Map;

public class Roman_to_Integer_13 {
    Map<Character, Integer> symbolValues = new HashMap<Character, Integer>() {{
        put('I', 1);
        put('V', 5);
        put('X', 10);
        put('L', 50);
        put('C', 100);
        put('D', 500);
        put('M', 1000);
    }};

    public int romanToInt(String s) {
        if (s.length() == 1) {
            return symbolValues.get(s.charAt(0));
        }
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            res += symbolValues.get(s.charAt(i));
        }
        for (int i = 1; i < s.length(); i++) {
            if (symbolValues.get(s.charAt(i - 1)) < symbolValues.get(s.charAt(i))) {
                res -= symbolValues.get(s.charAt(i - 1)) * 2;
            }
        }
        return res;

    }

    public int romanToInt1(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            int cur = symbolValues.get(s.charAt(i));
            if (i+1<s.length() && cur < symbolValues.get(s.charAt(i+1))){
                res -= cur;
                continue;
            }
            res += cur;
        }
        return res;
    }

}
