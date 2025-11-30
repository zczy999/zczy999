package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 13. 罗马数字转整数
 *
 * 罗马数字规则：
 * - 通常情况：小的数字在大的数字右边，表示相加（如 VI = 5 + 1 = 6）
 * - 特殊情况：小的数字在大的数字左边，表示相减（如 IV = 5 - 1 = 4）
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class Roman_to_Integer_13 {

    // 罗马字符到数值的映射
    Map<Character, Integer> symbolValues = new HashMap<Character, Integer>() {{
        put('I', 1);
        put('V', 5);
        put('X', 10);
        put('L', 50);
        put('C', 100);
        put('D', 500);
        put('M', 1000);
    }};

    /**
     * 解法一：两次遍历
     * 思路：先假设全部相加，再回头修正需要相减的情况
     *
     * 例如 "XIV" = 10 + 1 + 5 = 16，但 I 在 V 左边应该减
     * 所以要减去 1 * 2 = 2，最终 16 - 2 = 14
     */
    public int romanToInt(String s) {
        if (s.length() == 1) {
            return symbolValues.get(s.charAt(0));
        }
        int res = 0;
        // 第一次遍历：全部累加
        for (int i = 0; i < s.length(); i++) {
            res += symbolValues.get(s.charAt(i));
        }
        // 第二次遍历：遇到"小在大左边"的情况，减去多加的两倍
        for (int i = 1; i < s.length(); i++) {
            if (symbolValues.get(s.charAt(i - 1)) < symbolValues.get(s.charAt(i))) {
                res -= symbolValues.get(s.charAt(i - 1)) * 2;
            }
        }
        return res;
    }

    /**
     * 解法二：一次遍历（最优）
     * 思路：从左到右遍历，如果当前值 < 下一个值，说明是减法情况
     *
     * 例如 "XIV"：
     * - X(10)：下一个是 I(1)，10 > 1，加 10
     * - I(1)：下一个是 V(5)，1 < 5，减 1
     * - V(5)：没有下一个，加 5
     * - 结果：10 - 1 + 5 = 14
     */
    public int romanToInt1(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            int cur = symbolValues.get(s.charAt(i));
            // 当前值 < 下一个值，说明是减法情况（如 IV、IX）
            if (i + 1 < s.length() && cur < symbolValues.get(s.charAt(i + 1))) {
                res -= cur;
                continue;
            }
            res += cur;
        }
        return res;
    }

}
