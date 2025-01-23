package ZuoGod.DynamicProgramming;

import java.util.Arrays;

public class Profitable_Schemes_879 {

    int mod = (int) 1e9 + 7;

    public int profitableSchemes(int n, int minProfit, int[] group, int[] profit) {
        int[][][] dp = new int[group.length + 1][minProfit + 1][n + 1];
        for (int i = 0; i < group.length + 1; i++) {
            for (int j = 0; j < minProfit + 1; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        return f(0, n, minProfit, group, profit, dp);

    }

    private int f(int cur, int n, int minProfit, int[] group, int[] profit, int[][][] dp) {
        if (cur == group.length) {
            return minProfit == 0 ? 1 : 0;
        }
        if (dp[cur][minProfit][n] != -1) {
            return dp[cur][minProfit][n];
        }
        int res = f(cur + 1, n, minProfit, group, profit, dp) % mod;
        if (n >= group[cur]) {
            res = (res + f(cur + 1, n - group[cur], Math.max(0, minProfit - profit[cur]), group, profit, dp)) % mod;
        }
        dp[cur][minProfit][n] = res;
        return res;
    }
}
