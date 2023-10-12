package leetcode;

public class Is_Subsequence_392 {
    public boolean isSubsequence(String s, String t) {
        int len = t.length();
        int[][] dp = new int[len + 1][26];

        for (int i = len; i >= 0; i--) {
            for (int j = 0; j < 26; j++) {
                if (i == len) {
                    dp[i][j] = len;
                    continue;
                }
                if (t.charAt(i) - 'a' == j) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = dp[i + 1][j];
                }
            }
        }
        int index = 0;
        for (int i = 0; i < s.length(); i++) {
            if (dp[index][s.charAt(i) - 'a'] == len){
                return false;
            }
            index = dp[index][s.charAt(i) - 'a'] + 1;
        }
        return true;
    }

}
