package ZuoGod.DynamicProgramming;

import com.sun.jdi.ArrayReference;

import java.util.Arrays;

public class Scramble_String_87 {

    public boolean isScramble(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        int len = s1.length() + 1;
        int[][][] dp = new int[len][len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        return f(s1, s2, 0, 0, s1.length(), dp);
    }

    private boolean f(String s1, String s2, int i, int j, int len, int[][][] dp) {
        if (len == 1) {
            return s1.charAt(i) == s2.charAt(j);
        }
        if (dp[i][j][len] != -1) {
            return dp[i][j][len] == 1;
        }
        boolean res = false;
        for (int k = 1; k < len; k++) {
            res = f(s1, s2, i, j, k, dp) && f(s1, s2, i + k, j + k, len - k, dp);
            res = res || f(s1, s2, i, j + len - k, k, dp) && f(s1, s2, i + k, j, len - k, dp);
            if (res) {
                break;
            }
        }
        dp[i][j][len] = res ? 1 : 0;
        return res;
    }

}
