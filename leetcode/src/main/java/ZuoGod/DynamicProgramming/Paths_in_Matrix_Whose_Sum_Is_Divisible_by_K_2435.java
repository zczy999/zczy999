package ZuoGod.DynamicProgramming;

import java.util.Arrays;

public class Paths_in_Matrix_Whose_Sum_Is_Divisible_by_K_2435 {

    int n, m, k;
    int mod = 1000000007;

    public int numberOfPaths(int[][] grid, int k) {
        n = grid.length;
        m = grid[0].length;
        this.k = k;
        int[][][] dp = new int[n][m][k];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        return f(grid, 0, 0, 0, dp);
    }

    private int f(int[][] grid, int i, int j, int r, int[][][] dp) {
        if (i == n - 1 && j == m - 1) {
            return grid[i][j] % k == r ? 1 : 0;
        }
        if (i < 0 || i == n || j < 0 || j == m) {
            return 0;
        }
        if (dp[i][j][r] != -1) {
            return dp[i][j][r];
        }
        int cur = (r - grid[i][j] % k + k) % k;

        int ans = (f(grid, i + 1, j, cur, dp) % mod + f(grid, i, j + 1, cur, dp) % mod) % mod;
        dp[i][j][r] = ans;
        return ans;
    }

}
