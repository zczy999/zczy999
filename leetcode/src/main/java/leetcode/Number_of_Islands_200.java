package leetcode;

import java.util.*;

public class Number_of_Islands_200 {

    /**
     * 注意二维坐标转换时为 i*m + j,感觉还是麻烦了，不如再来个二维数组flag[][]
     * @param grid
     * @return
     */
    public int numIslands(char[][] grid) {
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
