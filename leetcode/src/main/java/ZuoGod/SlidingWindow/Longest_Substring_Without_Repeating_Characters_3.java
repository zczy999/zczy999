package ZuoGod.SlidingWindow;

import java.util.HashMap;
import java.util.Map;

public class Longest_Substring_Without_Repeating_Characters_3 {
    public static void main(String[] args) {
        Longest_Substring_Without_Repeating_Characters_3 res = new Longest_Substring_Without_Repeating_Characters_3();
        int i = res.lengthOfLongestSubstring("abba");
        System.out.println(i);
    }

    public int lengthOfLongestSubstring(String s) {


        return 0;
    }



    public int lengthOfLongestSubstring1(String s) {
        Map<Character, Integer> map = new HashMap<>();

        int index = 0;
        int left = 0;
        int max = 0;
        while (index < s.length()) {
            if (map.containsKey(s.charAt(index))) {
                //这个max很重要，排除不在这个滑动窗口里的值，这种情况下不需要更新
                left = Math.max(left,map.get(s.charAt(index)) + 1);
            }

            map.put(s.charAt(index), index);
            max = Math.max(max, index - left + 1);
            index++;
        }
        return max;
    }

    public int lengthOfLongestSubstring2(String s) {
        Map<Character, Integer> map = new HashMap<>();

        int index = 0;
        int res = 0;
        int max = 0;
        while (index < s.length()) {
            if (map.containsKey(s.charAt(index))) {
                res = 0;
                index = map.get(s.charAt(index)) + 1;
                map.clear();
            } else {
                map.put(s.charAt(index), index);
                res++;
                max = Math.max(max, res);
                index++;
            }
        }
        return max;
    }

}
