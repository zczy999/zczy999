package leetcode;

import java.util.*;

public class Number_of_Islands_200 {
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    /**
     * dfs
     *
     * @param grid
     * @return
     */
    public int numIslands(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int sum = 0;
        int[][] flag = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (flag[i][j] == 1 || grid[i][j] == '0') {
                    continue;
                }
                sum++;
                dfs(i, j, flag, grid);
            }
        }
        return sum;
    }

    private void dfs(int i, int j, int[][] flag, char[][] grid) {
        int n = flag.length;
        int m = flag[0].length;
        if (i >= n || i < 0 || j >= m || j < 0 || flag[i][j] == 1 || grid[i][j] == '0') {
            return;
        }
        flag[i][j] = 1;
        for (int[] dir : directions) {
            int nexti = i + dir[0];
            int nextj = j + dir[1];
            dfs(nexti, nextj, flag, grid);
        }

    }

    /**
     * bfs
     *
     * @param grid
     * @return
     */
    public int numIslands1(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int sum = 0;
        int[][] flag = new int[n][m];
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (flag[i][j] == 1 || grid[i][j] == '0') {
                    continue;
                }
                sum++;
                int res = i * m + j;
                queue.add(res);
                flag[i][j] = 1;
                while (!queue.isEmpty()) {
                    Integer poll = queue.poll();
                    int pollRow = poll / m;
                    int pollLine = poll % m;
                    for (int[] point : getNextPoint(pollRow, pollLine, n, m)) {
                        int pi = point[0];
                        int pj = point[1];
                        if (flag[pi][pj] == 1 || grid[pi][pj] == '0') {
                            continue;
                        }
                        queue.add(pi * m + pj);
                        flag[pi][pj] = 1;
                    }
                }
            }
        }
        return sum;
    }

    private int[][] getNextPoint(int i, int j, int n, int m) {
        int[][] res = new int[4][2];
        int num = 0;
        for (int[] dir : directions) {
            int nexti = i + dir[0];
            int nextj = j + dir[1];
            if (nexti >= n || nexti < 0 || nextj >= m || nextj < 0) {
                continue;
            }
            res[num] = new int[]{nexti, nextj};
            num++;
        }
        return res;
    }


    /**
     * 使用BFS
     * 注意二维坐标转换时为 i*m + j,感觉还是麻烦了，不如再来个二维数组flag[][]
     *
     * @param grid
     * @return
     */
    public int numIslands2(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        Queue<Integer> queue = new LinkedList<>();
        HashSet<Integer> set = new HashSet<>();
        int number = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '0') {
                    continue;
                }
                int res = i * m + j;
                if (set.contains(res)) {
                    continue;
                }
                number++;
                queue.add(res);
                set.add(res);

                while (!queue.isEmpty()) {
                    int poll = queue.poll();
                    for (int k : getNext(poll, grid)) {
                        if (set.contains(k)) {
                            continue;
                        }
                        queue.add(k);
                        set.add(k);
                    }
                }
            }
        }
        return number;

    }

    private List<Integer> getNext(int poll, char[][] grid) {
        List<Integer> list = new ArrayList<>();
        int n = grid.length;
        int m = grid[0].length;

        int pollRow = poll / m;
        int pollLine = poll % m;
        if (pollRow - 1 >= 0 && grid[pollRow - 1][pollLine] == '1') {
            list.add((pollRow - 1) * m + pollLine);
        }
        if (pollLine - 1 >= 0 && grid[pollRow][pollLine - 1] == '1') {
            list.add((pollRow) * m + (pollLine - 1));
        }
        if (pollRow + 1 < n && grid[pollRow + 1][pollLine] == '1') {
            list.add((pollRow + 1) * m + pollLine);
        }
        if (pollLine + 1 < m && grid[pollRow][pollLine + 1] == '1') {
            list.add(pollRow * m + (pollLine + 1));
        }
        return list;

    }


}
