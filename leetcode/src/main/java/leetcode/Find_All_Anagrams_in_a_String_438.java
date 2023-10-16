package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Find_All_Anagrams_in_a_String_438 {
    public static void main(String[] args) {
        Find_All_Anagrams_in_a_String_438 res = new Find_All_Anagrams_in_a_String_438();
        String s = "abaacbabc";
        String p = "abc";
        List<Integer> anagrams = res.findAnagrams(s, p);
        System.out.println(anagrams.toString());
    }

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        if (s.length() < p.length()) {
            return res;
        }

        Map<Character, Integer> maps = new HashMap<>();
        for (int i = 0; i < p.length(); i++) {
            maps.put(p.charAt(i), maps.getOrDefault(p.charAt(i), 0) + 1);
        }

        int left = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);

            while (map.getOrDefault(s.charAt(i), 0) > maps.getOrDefault(s.charAt(i), 0)) {
                map.put(s.charAt(left), map.get(s.charAt(left)) - 1);
                left++;
            }
            if (i - left + 1 == p.length()) {
                res.add(left);
            }

        }
        return res;

    }
}
