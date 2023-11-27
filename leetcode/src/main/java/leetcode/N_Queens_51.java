package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class N_Queens_51 {
    public static void main(String[] args) {
        N_Queens_51 res = new N_Queens_51();
        int[][] flag = new int[4][4];
        res.findQueenDir(2, 3, flag, 2);
        System.out.println(Arrays.deepToString(flag));


    }

    int[][] flag;
    List<List<String>> res = new ArrayList<>();

    public List<List<String>> solveNQueens(int n) {
        flag = new int[n][n];
        dfs(0, 0, 1);
        return null;
    }

    private void dfs(int i, int j, int len) {

        findQueenDir(i, j, flag, 1);
        flag[i][j] = 2;
        if (len == flag.length) {
            getResString
            return;
        }
        for (int[] pos : findNextPos()){
            dfs(pos[0],pos[j],);
        }


    }

    private List<int[]> findNextPos() {
        List<int[]> res = new ArrayList<>();
        for (int i = 0; i < flag.length; i++) {
            for (int j = 0; j < flag.length; j++) {
                if (flag[i][j] == 0) {
                    res.add(new int[]{i, j});
                }
            }
        }
        return res;
    }

    public void findQueenDir(int i, int j, int[][] flag, int value) {

        for (int k = 0; k < flag.length; k++) {
            flag[i][k] = value;
            flag[k][j] = value;
        }
        int row = i; // 指定点的行
        int col = j; // 指定点的列
        int n = flag.length;
        // 遍历从左上到右下的斜线
        int minDist = Math.min(row, col); // 最小距离
        int startRow = row - minDist; // 斜线起始行
        int startCol = col - minDist; // 斜线起始列

        while (startRow < n && startCol < n) {
            flag[startRow][startCol] = value;
            startRow++;
            startCol++;
        }

        // 遍历从右上到左下的斜线
        minDist = Math.min(row, n - 1 - col); // 最小距离
        startRow = row - minDist; // 斜线起始行
        startCol = col + minDist; // 斜线起始列

        while (startRow < n && startCol >= 0) {
            flag[startRow][startCol] = value;
            startRow++;
            startCol--;
        }
    }
}
