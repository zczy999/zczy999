package leetcode;

/**
 * 12. 整数转罗马数字
 *
 * 贪心算法：从大到小依次匹配，每次尽可能使用最大的罗马数字
 *
 * 关键点：将 6 个特殊减法组合（CM、CD、XC、XL、IX、IV）也加入映射表
 * 这样就不用单独处理减法规则，统一用贪心匹配即可
 *
 * 时间复杂度：O(1) - 循环次数有上限（num 最大 3999，最多约 15 次）
 * 空间复杂度：O(1) - 固定大小的数组
 */
public class Integer_to_Roman_12 {

    /**
     * 贪心解法
     *
     * 例如 num = 1994：
     * - 1994 >= 1000，减去 1000，追加 "M"，剩余 994
     * - 994 >= 900，减去 900，追加 "CM"，剩余 94
     * - 94 >= 90，减去 90，追加 "XC"，剩余 4
     * - 4 >= 4，减去 4，追加 "IV"，剩余 0
     * - 结果："MCMXCIV"
     */
    public String intToRoman(int num) {
        // 数值数组：从大到小排列，包含 6 个特殊减法组合
        int[] integers = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        // 对应的罗马数字
        String[] romanNumerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder sb = new StringBuilder();
        // 从大到小遍历，贪心匹配
        for (int i = 0; i < integers.length; i++) {
            // 当前数值能用几次就用几次
            while (num >= integers[i]) {
                num -= integers[i];
                sb.append(romanNumerals[i]);
            }
        }
        return sb.toString();
    }
}
