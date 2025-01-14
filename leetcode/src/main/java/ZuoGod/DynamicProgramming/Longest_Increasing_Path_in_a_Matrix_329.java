package ZuoGod.DynamicProgramming;

import java.util.Arrays;

public class Longest_Increasing_Path_in_a_Matrix_329 {

    int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    int[][] dp;

    public int longestIncreasingPath(int[][] matrix) {

        int n = matrix.length;
        int m = matrix[0].length;
        dp = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }
        int res = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res = Math.max(res, f(matrix, i, j));
            }

        }
        return res;
    }

    private int f(int[][] matrix, int i, int j) {
        int n = matrix.length;
        int m = matrix[0].length;
        if (i < 0 || i >= n || j < 0 || j >= m) {
            return 0;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        int res = 1;
        for (int[] dir : dirs) {
            int nextI = i + dir[0];
            int nextJ = j + dir[1];
            if (nextI >= 0 && nextI < n && nextJ >= 0 && nextJ < m && matrix[nextI][nextJ] > matrix[i][j]){
                res = Math.max(res, f(matrix, nextI, nextJ) + 1);
            }
        }
        dp[i][j] = res;
        return res;
    }

}
