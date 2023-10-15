package leetcode;

import java.util.*;

public class Substring_with_Concatenation_of_All_Words_30 {
    public List<Integer> findSubstring(String s, String[] words) {
        Map<String, Integer> sets = new HashMap<>();
        List<Integer> res = new ArrayList<>();
        for (String word : words) {
            sets.put(word, sets.getOrDefault(word, 0) + 1);
        }

        int n = words[0].length();
        int sum = n * words.length;

        for (int i = 0; i < s.length() - sum + 1; i++) {
            //substring重点
            String tmp = s.substring(i, i + sum);
            Map<String, Integer> set = new HashMap<>();
            for (int j = 0; j < sum; j = j + n) {
                String w = tmp.substring(j, j + n);
                set.put(w, set.getOrDefault(w, 0) + 1);
            }
            if (set.equals(sets)) {
                res.add(i);
            }
        }
        return res;
    }

}
