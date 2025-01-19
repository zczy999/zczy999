package ZuoGod.DynamicProgramming;

import java.util.Arrays;

public class Edit_Distance_72 {

    public int minDistance(String word1, String word2) {
        char[] word1CharArray = word1.toCharArray();
        char[] word2CharArray = word2.toCharArray();
        int[][] dp = new int[word1.length()][word2.length()];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }
        return f(word1CharArray, 0, word2CharArray, 0, dp);

    }

    private int f(char[] word1, int i, char[] word2, int j, int[][] dp) {
        if (j == word2.length) {
            return word1.length - i;
        }
        if (i == word1.length) {
            return word2.length - j;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        if (word1[i] == word2[j]) {
            return f(word1, i + 1, word2, j + 1, dp);
        }
        int res = f(word1, i + 1, word2, j, dp);
        res = Math.min(res, f(word1, i + 1, word2, j + 1, dp));
        res = Math.min(res, f(word1, i, word2, j + 1, dp));
        return dp[i][j] = ++res;
    }

}
