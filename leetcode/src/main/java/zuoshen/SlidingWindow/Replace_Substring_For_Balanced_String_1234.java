package zuoshen.SlidingWindow;

import java.util.HashMap;
import java.util.Map;

public class Replace_Substring_For_Balanced_String_1234 {
    public static void main(String[] args) {
        Replace_Substring_For_Balanced_String_1234 res = new Replace_Substring_For_Balanced_String_1234();
        String s = "WQWRQQQW";
        int i = res.balancedString(s);
        System.out.println(i);
    }

    public int balancedString(String s) {
        char[] charArray = s.toCharArray();
        int[] map = new int['X']; // 字串外部的各个字符出现次数
        for (int i = 0; i < charArray.length; i++) {
            map[charArray[i]]++;
        }
        int replaceTimes = 0;
        if (check(map, replaceTimes, charArray)) {
            return 0;
        }

        int res = Integer.MAX_VALUE;
        for (int left = 0, right = 0; right <= charArray.length; right++) {
            while (left < right && check(map, replaceTimes, charArray)) {
                res = Math.min(res, right - left);
                map[charArray[left]]++;
                left++;
                replaceTimes--;
            }
            if (right == charArray.length){
                break;
            }
            map[charArray[right]]--;
            replaceTimes++;
        }

        return res;
    }

    private boolean check(int[] map, int replaceTimes, char[] charArray) {
        char[] chars = {'Q', 'W', 'E', 'R'};
        int avg = charArray.length / 4;
        int count = 0;
        for (char ch : chars) {
            count += Math.abs(avg - map[ch]);
        }
        return replaceTimes >= count;
    }
}
