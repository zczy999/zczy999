package ZuoGod.DynamicProgramming;

public class Ones_and_Zeroes_474 {

    int oneNums, zeroNums;

    public int findMaxForm(String[] strs, int m, int n) {

        int[][][] dp = new int[strs.length + 1][m + 1][n + 1];
        for (int i = 0; i < dp.length; i++){
            for (int j = 0; j < dp[0].length; j++) {
                for (int k = 0; k < dp[0][0].length; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }
        return f(strs, 0, m, n, dp);

    }

    private int f(String[] strs, int i, int m, int n, int[][][] dp) {
        if (i == strs.length) {
            return 0;
        }
        if (dp[i][m][n] != -1){
            return dp[i][m][n];
        }

        int res = f(strs, i + 1, m, n, dp);

        countOneAndZero(strs[i]);
        if (m >= zeroNums && n >= oneNums) {
            res = Math.max(res, 1 + f(strs, i + 1, m - zeroNums, n - oneNums, dp));
        }
        dp[i][m][n] = res;
        return res;
    }

    private void countOneAndZero(String str) {
        oneNums = 0;
        zeroNums = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                oneNums++;
            } else {
                zeroNums++;
            }
        }
    }

}
