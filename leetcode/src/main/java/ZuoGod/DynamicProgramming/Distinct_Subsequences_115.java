package ZuoGod.DynamicProgramming;

import java.util.Arrays;

public class Distinct_Subsequences_115 {

    int mod = 1000000007;

    public int numDistinct(String s, String t) {
        char[] sCharArray = s.toCharArray();
        char[] tCharArray = t.toCharArray();
        int[][] dp = new int[s.length()][t.length()];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }
        return f(sCharArray, 0, tCharArray, 0, dp);
    }

    public int f(char[] s, int i, char[] t, int j, int[][] dp) {
        if (j == t.length) {
            return 1;
        }
        if (i == s.length) {
            return 0;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        int res = f(s, i + 1, t, j, dp);
        if (s[i] == t[j]) {
            res = res + f(s, i + 1, t, j + 1, dp) % mod;
        }
        return dp[i][j] = res % mod;
    }

    /**
     * 这个v存在一看就不太对 因为无法缓存
     *
     * @param charArray
     * @param t
     * @param i
     * @param v
     * @return
     */
    public int f(char[] charArray, String t, int i, String v) {
        if (i == charArray.length) {
            return t.equals(v) ? 1 : 0;
        }
        if (v.length() == t.length()) {
            return t.equals(v) ? 1 : 0;
        }
        int res = f(charArray, t, i + 1, v);
        res += f(charArray, t, i + 1, v + charArray[i]);
        return res;
    }

}
