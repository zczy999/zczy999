package leetcode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Minimum_Window_Substring_76 {
    public static void main(String[] args) {
        Minimum_Window_Substring_76 res = new Minimum_Window_Substring_76();
        String s = "ADOBECODEBANC";
        String t = "ABC";
        String s1 = res.minWindow1(s, t);
        System.out.println(s1);
    }

    public String minWindow(String s, String t) {
        if (s == null || s.length() == 0 || t == null || t.length() == 0) return "";
        int[] need = new int[128];
        for (int i = 0; i < t.length(); i++) {
            need[t.charAt(i)]++;
        }

        int l = 0, r = 0, start = 0, minLen = Integer.MAX_VALUE, count = t.length();
        while (r < s.length()) {
            char c = s.charAt(r);
            if (need[c] > 0) count--;
            need[c]--;

            if (count == 0) {
                char lc = s.charAt(l);
                while (l < r && need[lc] < 0) {
                    need[lc]++;
                    l++;
                    lc = s.charAt(l);
                }
                if (r - l + 1 < minLen) {
                    minLen = r - l + 1;
                    start = l;
                }
                // one case satisfied, start a new case
                // need[lc]++;
                // count++;
                // l++;
            }
            r++;
        }

        return minLen != Integer.MAX_VALUE ? s.substring(start, start + minLen) : "";

    }

    public String minWindow1(String s, String t) {
        Map<Character, Integer> maps = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            maps.put(t.charAt(i), maps.getOrDefault(t.charAt(i), 0) + 1);
        }

        Map<Character, Integer> map = new HashMap<>();
        int min = Integer.MAX_VALUE;
        int l = 0;
        String res = "";
        for (int r = 0; r < s.length(); r++) {
            char c = s.charAt(r);
            if (maps.containsKey(c)) {
                map.put(c, map.getOrDefault(c, 0) + 1);
            }

            while (check(map, maps)) {
                if ((r - l + 1) < min) {
                    min = r - l + 1;
                    res = s.substring(l, r + 1);
                }
                if (maps.containsKey(s.charAt(l))) {
                    map.put(s.charAt(l), map.get(s.charAt(l)) - 1);
                }
                l++;
            }
        }
        return res;

    }

    private boolean check(Map<Character, Integer> map, Map<Character, Integer> maps) {
        Iterator iterator = maps.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Character key = (Character) entry.getKey();
            Integer val = (Integer) entry.getValue();
            if (map.getOrDefault(key, 0) < val) {
                return false;
            }
        }

        return true;
    }
}
