package leetcode;

import java.util.ArrayList;
import java.util.List;

public class word_search_79 {
    public static void main(String[] args) {
//        String[][] s = {{"A", "B", "C", "E"}, {"S", "F", "C", "S"}, {"A", "D", "E", "E"}};
        String[][] s = {{"a","b"}, {"c","d"}};
        char[][] board = new char[s.length][s[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = s[i][j].charAt(0);
            }
        }
        word_search_79 res = new word_search_79();
        boolean f = res.exist(board, "cdba");
        System.out.println(f);
    }

    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public boolean exist(char[][] board, String word) {
        int n = board.length;
        int m = board[0].length;
        List<int[]> list = new ArrayList<>();
        char c = word.charAt(0);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == c) {
                    list.add(new int[]{i, j});
                }
            }
        }
        if (list.isEmpty()) {
            return false;
        }
        char[] charArray = word.toCharArray();
        for (int[] cur : list) {
            int[][] flag = new int[n][m];
            if (dfs(cur, board, charArray, flag, 0)) {
                return true;
            }
        }
        return false;
    }

    private boolean dfs(int[] cur, char[][] board, char[] word, int[][] flag, int s) {
        if (s == word.length) {
            return true;
        }

        int i = cur[0];
        int j = cur[1];
        int n = board.length;
        int m = board[0].length;
        if (i >= n || i < 0 || j >= m || j < 0 || flag[i][j] == 1 || word[s] != board[i][j]) {
            return false;
        }
        flag[i][j] = 1;
        for (int[] dir : directions) {
            int nexti = i + dir[0];
            int nextj = j + dir[1];
            boolean f = dfs(new int[]{nexti, nextj}, board, word, flag, s + 1);
            if (f) {
                return true;
            }
        }
        flag[i][j] = 0;
        return false;
    }

}
