package ZuoGod.DynamicProgramming;

import java.util.Arrays;

public class Longest_Palindromic_Subsequence_516 {

    private int[][] dp;
    public int longestPalindromeSubseq(String s) {
        char[] charArray = s.toCharArray();

        dp = new int[charArray.length][charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            Arrays.fill(dp[i], -1);
        }
        return f(charArray, 0, charArray.length - 1);
    }

    private int f(char[] charArray, int l, int r) {
        if (l > r) {
            return 0;
        }
        if (l == r) {
            return 1;
        }
        if (dp[l][r] != -1){
            return dp[l][r];
        }
        int res = 0;
        if (charArray[l] == charArray[r]) {
            res = f(charArray, l + 1, r - 1) + 2;
        }
        res = Math.max(res, Math.max(f(charArray, l + 1, r), f(charArray, l, r - 1)));
        dp[l][r] = res;
        return res;
    }

}
