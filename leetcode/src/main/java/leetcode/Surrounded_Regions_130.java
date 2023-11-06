package leetcode;

public class Surrounded_Regions_130 {

    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public void solve(char[][] board) {
        int n = board.length;
        int m = board[0].length;
        int[][] flag = new int[n][m];

        for (int i = 0; i < n; i++) {
            if (board[i][0] == 'O') {
                dfs(i, 0, flag, board);
            }
            if (board[i][m - 1] == 'O') {
                dfs(i, m - 1, flag, board);
            }
        }

        for (int j = 0; j < m; j++) {
            if (board[0][j] == 'O') {
                dfs(0, j, flag, board);
            }
            if (board[n - 1][j] == 'O') {
                dfs(n - 1, j, flag, board);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (flag[i][j] == 1) {
                    board[i][j] = 'O';
                } else {
                    board[i][j] = 'X';
                }
            }
        }


    }

    private void dfs(int i, int j, int[][] flag, char[][] grid) {
        int n = flag.length;
        int m = flag[0].length;
        if (i >= n || i < 0 || j >= m || j < 0 || flag[i][j] == 1 || grid[i][j] == 'X') {
            return;
        }
        flag[i][j] = 1;
        for (int[] dir : directions) {
            int nexti = i + dir[0];
            int nextj = j + dir[1];
            dfs(nexti, nextj, flag, grid);
        }

    }

}
