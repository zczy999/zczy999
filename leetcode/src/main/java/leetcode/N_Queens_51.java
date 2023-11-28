package leetcode;

import java.util.*;

public class N_Queens_51 {
    public static void main(String[] args) {
        N_Queens_51 res = new N_Queens_51();
        List<List<String>> lists = res.solveNQueens(4);
        System.out.println(lists.toString());

    }

    int[][] flag;
    List<List<String>> res = new ArrayList<>();

    public List<List<String>> solveNQueens(int n) {
        Set<Integer> slashSet1 = new HashSet<>();
        Set<Integer> slashSet2 = new HashSet<>();
        Set<Integer> lineSet = new HashSet<>();

        List<Integer> pos = new ArrayList<>();
        dfs(0, 0, n, slashSet1, slashSet2, lineSet, pos);

        return res;
    }

    private void dfs(int i, int len, int n, Set<Integer> slashSet1, Set<Integer> slashSet2, Set<Integer> lineSet, List<Integer> pos) {
        if (len == n) {
            List<String> list = getStringByPos(pos, n);
            res.add(list);
            return;
        }

        for (int j = 0; j < n; j++) {
            int slash1 = i + j;
            int slash2 = i - j;
            if (slashSet1.contains(slash1) || slashSet2.contains(slash2) || lineSet.contains(j)) {
                continue;
            }
            slashSet1.add(slash1);
            slashSet2.add(slash2);
            lineSet.add(j);
            pos.add(j);
            dfs(i + 1, len + 1, n, slashSet1, slashSet2, lineSet, pos);
            slashSet1.remove(slash1);
            slashSet2.remove(slash2);
            lineSet.remove(j);
            pos.remove(pos.size() - 1);
        }

    }

    private List<String> getStringByPos(List<Integer> pos, int n) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Integer cur = pos.get(i);
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < n; j++) {
                if (j == cur){
                    sb.append("Q");
                    continue;
                }
                sb.append(".");
            }
            res.add(sb.toString());
        }
        return res;
    }

    private void dfs1(int i, int j, int len) {

        findQueenDir(i, j, flag, 1);
        flag[i][j] = 2;
        if (len == flag.length) {
//            getResString
            return;
        }
        for (int[] pos : findNextPos()) {
            dfs1(pos[0], pos[j], len + 1);
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
