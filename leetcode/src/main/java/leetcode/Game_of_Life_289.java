package leetcode;

import utils.LeetcodeInput;

import java.util.Arrays;

public class Game_of_Life_289 {

    public static void main(String[] args) {
        String json = "[[0,1,0],[0,0,1],[1,1,1],[0,0,0]]";
        int[][] board = LeetcodeInput.fromJson(json, int[][].class);
        Game_of_Life_289 res = new Game_of_Life_289();
        res.gameOfLife(board);
        System.out.println(Arrays.deepToString(board));
    }

    public void gameOfLife(int[][] board) {
        int n = board.length;
        int m = board[0].length;

        int[][] preSum = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int num = getAroundCell(i, j, board);
                preSum[i][j] = num;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 0) {
                    if (preSum[i][j] == 3) {
                        board[i][j] = 1;
                    }
                } else {
                    if (preSum[i][j] < 2 || preSum[i][j] > 3) {
                        board[i][j] = 0;
                    }
                }
            }

        }

    }

    private int getAroundCell(int i, int j, int[][] board) {
        int n = board.length;
        int m = board[0].length;
        int res = 0;
        for (int k = i - 1; k <= i + 1; k++) {
            for (int l = j - 1; l <= j + 1; l++) {
                if (k < 0 || k >= n || l < 0 || l >= m) {
                    continue;
                }
                if (k == i && l == j) {
                    continue;
                }
                if (board[k][l] == 1) {
                    res++;
                }
            }
        }


        return res;
    }


}
