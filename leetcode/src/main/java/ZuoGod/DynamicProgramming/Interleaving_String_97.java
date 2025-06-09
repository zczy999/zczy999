package ZuoGod.DynamicProgramming;

import java.util.Arrays;

public class Interleaving_String_97 {

    private char[] s1Array, s2Array;

    public boolean isInterleave(String s1, String s2, String s3) {
        s1Array = s1.toCharArray();
        s2Array = s2.toCharArray();
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        if (s1.length() == 0 && s2.length() == 0 && s3.length() == 0) {
            return true;
        }

        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }

        return func(0, 0, s3, dp);
    }

    /**
     * 功能描述：检查是否可以通过s1和s2的字符交织组成s3
     *
     * @param i  s1的索引
     * @param j  s2的索引
     * @param s3 待检查的字符串
     * @param dp 动态规划数组，用于缓存已计算的结果
     * @return 如果可以通过s1和s2的字符交织组成s3，则返回true；否则返回false
     */
    private boolean func(int i, int j, String s3, int[][] dp) {
        // 当s1和s2的所有字符都检查完毕时，说明成功交织成s3
        if (i == s1Array.length && j == s2Array.length) {
            return true;
        }
        // 如果当前状态已经计算过，则直接返回结果，避免重复计算
        if (dp[i][j] != -1) {
            return dp[i][j] == 1;
        }
        boolean res = false;
        // 检查s1的当前字符是否可以作为s3的下一个字符
        if (i < s1Array.length && s1Array[i] == s3.charAt(i + j)) {
            res = res || func(i + 1, j, s3, dp);
        }
        // 检查s2的当前字符是否可以作为s3的下一个字符
        if (j < s2Array.length && s2Array[j] == s3.charAt(i + j)) {
            res = res || func(i, j + 1, s3, dp);
        }
        // 缓存当前状态的结果，1表示可以交织，0表示不可以
        dp[i][j] = res ? 1 : 0;
        return res;
    }


}
