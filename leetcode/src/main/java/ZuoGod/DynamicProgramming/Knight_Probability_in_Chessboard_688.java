package ZuoGod.DynamicProgramming;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Knight_Probability_in_Chessboard_688 {
    // 马能走的 8 种跳法
    private static final int[][] dirs = {
            {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
            {1, -2}, {1, 2}, {2, -1}, {2, 1}
    };

    // 记忆表 dp[k][r][c] = 从 (r,c) 出发, 再走 k 步后 仍在棋盘内的概率
    private double[][][] dp;

    public double knightProbability(int n, int k, int row, int column) {
        dp = new double[k+1][n][n];
        // 用 -1 表示未计算
        for(int step=0; step<=k; step++){
            for(int r=0; r<n; r++){
                Arrays.fill(dp[step][r], -1.0);
            }
        }
        return f(n, k, row, column);
    }

    // 递归函数: 从 (row,column) 出发, 走 k 步后仍在棋盘内的概率
    private double f(int n, int k, int row, int col) {
        // 递归边界1: 如果越界, 概率=0
        if (!isInChessboard(n, row, col)) {
            return 0.0;
        }
        // 递归边界2: 剩余步数=0, 并且 (row,col) 没出界, 概率=1
        if (k == 0) {
            return 1.0;
        }
        // 查缓存
        if (dp[k][row][col] != -1.0) {
            return dp[k][row][col];
        }

        // 往下计算
        double ans = 0.0;
        for(int[] dir : dirs){
            int nr = row + dir[0];
            int nc = col + dir[1];
            ans += f(n, k-1, nr, nc);
        }
        // 走一步分成 8 个方向, 所以要 / 8
        ans /= 8.0;

        dp[k][row][col] = ans;
        return ans;
    }

    private boolean isInChessboard(int n, int row, int col) {
        return 0 <= row && row < n && 0 <= col && col < n;
    }
}
