package zuoshen.DynamicProgramming;

import java.util.Arrays;

public class Longest_Valid_Parentheses_32 {
    public int longestValidParentheses(String s) {
        char[] charArray = s.toCharArray();
        int[] dp = new int[charArray.length + 1];
        Arrays.fill(dp, 0);
        for (int i = 1; i < charArray.length; i++) {
            if (charArray[i] == '(') {
                dp[i] = 0;
                continue;
            }
            int pre = dp[i - 1];
            //"()(())"
            if (i - 1 - pre >= 0) {
                if (charArray[i - 1 - pre] == '(') {
                    dp[i] = pre + 2;
                    if (i - 2 - pre >= 0) {
                        dp[i] += dp[i - 2 - pre];
                    }
                }
            }
        }
        int max = 0;
        for (int i : dp) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

}
