package leetcode;


import com.fasterxml.jackson.databind.ObjectMapper;
import utils.LeetcodeInput;

import java.util.ArrayList;
import java.util.List;


public class word_search_79 {
    public static void main(String[] args) throws Exception {
        String json = "[['A','B','C','E'],['S','F','C','S'],['A','D','E','E']]";
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
//        char[][] s = objectMapper.readValue(json, char[][].class);
        char[][] s = LeetcodeInput.fromJson(json, char[][].class);
        word_search_79 res = new word_search_79();
        boolean f = res.exist(s, "ABCCED");
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
