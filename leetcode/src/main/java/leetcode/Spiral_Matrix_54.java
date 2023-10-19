package leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Spiral_Matrix_54 {
    public List<Integer> spiralOrder(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        List<Integer> res = new ArrayList<>();
        int[][] flag = new int[m][n];
        int[][] direction = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        int time = 0;
        int len = 0;
        int i = 0, j = 0;
        while (len < m * n) {
            res.add(matrix[i][j]);
            flag[i][j] = 1;
            int nexti = i + direction[time][0];
            int nextj = j + direction[time][1];
            if (nexti >= m || nexti < 0 || nextj >= n || nextj < 0 || flag[nexti][nextj] == 1) {
                time++;
                time = time % 4;
            }
            i += direction[time][0];
            j += direction[time][1];
            len++;
        }
        return res;
    }
}
