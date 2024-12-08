package ZuoGod.DynamicProgramming;

// 布尔运算
// 给定一个布尔表达式和一个期望的布尔结果 result
// 布尔表达式由 0 (false)、1 (true)、& (AND)、 | (OR) 和 ^ (XOR) 符号组成
// 布尔表达式一定是正确的，不需要检查有效性
// 但是其中没有任何括号来表示优先级
// 你可以随意添加括号来改变逻辑优先级
// 目的是让表达式能够最终得出result的结果
// 返回最终得出result有多少种不同的逻辑计算顺序
// 测试链接 : https://leetcode.cn/problems/boolean-evaluation-lcci/
public class Boolean_Evaluation_LCCI {

    public int countEval(String s, int result) {

        char[] charArray = s.toCharArray();
        int len = charArray.length;
        int[][][] dp = new int[len][len][];
        int[] res = f(charArray, 0, len - 1, dp);
        return result == 1 ? res[0] : res[1];
    }

    private int[] f(char[] charArray, int l, int r, int[][][] dp) {
        if (dp[l][r] != null) {
            return dp[l][r];
        }
        if (l == r) {
            if (charArray[l] == '1') {
                return new int[]{1, 0};
            } else {
                return new int[]{0, 1};
            }
        }

        int trueResult = 0, falseResult = 0;

        for (int i = l + 1; i < r; i = i + 2) {
            int[] leftPart = f(charArray, l, i - 1, dp);
            int[] rightPart = f(charArray, i + 1, r, dp);
            if (charArray[i] == '&') {
                int trueRes = leftPart[0] * rightPart[0];
                int falseRes = leftPart[1] * rightPart[1] + leftPart[0] * rightPart[1] + leftPart[1] * rightPart[0];
                trueResult += trueRes;
                falseResult += falseRes;
            } else if (charArray[i] == '|') {
                int trueRes = leftPart[0] * rightPart[0] + leftPart[0] * rightPart[1] + leftPart[1] * rightPart[0];
                int falseRes = leftPart[1] * rightPart[1];
                trueResult += trueRes;
                falseResult += falseRes;
            } else {
                int trueRes = leftPart[0] * rightPart[1] + leftPart[1] * rightPart[0];
                int falseRes = leftPart[0] * rightPart[0] + leftPart[1] * rightPart[1];
                trueResult += trueRes;
                falseResult += falseRes;
            }
        }
        int[] res = {trueResult, falseResult};
        dp[l][r] = res;
        return res;
    }

}
