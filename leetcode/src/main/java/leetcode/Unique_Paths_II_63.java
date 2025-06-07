package leetcode;

public class Unique_Paths_II_63 {

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {

        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            if (obstacleGrid[i][0] == 1) {
                break; // 如果遇到障碍物，则后面的路径都为0
            }
            dp[i][0] = 1; // 初始化第一列
        }

        for (int j = 0; j < n; j++) {
            if (obstacleGrid[0][j] == 1) {
                break; // 如果遇到障碍物，则后面的路径都为0
            }
            dp[0][j] = 1; // 初始化第一行
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0; // 如果遇到障碍物，则路径为0
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1]; // 否则，路径为左方和上方路径之和
                }
            }
        }

        return dp[m - 1][n - 1];
    }

}
