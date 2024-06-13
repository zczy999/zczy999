package ZuoGod.Graph;

import java.util.ArrayDeque;
import java.util.Deque;

public class Shortest_Path_to_Get_All_Keys_864 {


    public int shortestPathAllKeys(String[] grid) {
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        Deque<int[]> deque = new ArrayDeque<>();
        int key = 0;
        int m = grid.length;
        int n = grid[0].length();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i].charAt(j) == '@') {
                    deque.addLast(new int[]{i, j, 0});
                }
                if (grid[i].charAt(j) >= 'a' && grid[i].charAt(j) <= 'f') {
                    key |= 1 << (grid[i].charAt(j) - 'a');
                }
            }
        }
        boolean[][][] visited = new boolean[m][n][key + 1];

        int level = 1;
        while (!deque.isEmpty()) {
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                int[] cur = deque.pollFirst();
                for (int[] dir : dirs) {
                    int nextX = cur[0] + dir[0];
                    int nextY = cur[1] + dir[1];
                    int nextZ = cur[2];
                    if (nextX < 0 || nextX >= m || nextY < 0 || nextY >= n || grid[nextX].charAt(nextY) == '#') {
                        continue;
                    }
                    if (grid[nextX].charAt(nextY) >= 'A' && grid[nextX].charAt(nextY) <= 'F'
                            && ((nextZ & (1 << grid[nextX].charAt(nextY) - 'A')) == 0)) {
                        continue;
                    }
                    if (grid[nextX].charAt(nextY) >= 'a' && grid[nextX].charAt(nextY) <= 'f'
                            && ((nextZ & (1 << grid[nextX].charAt(nextY) - 'a')) == 0)) {
                        nextZ |= (1 << (grid[nextX].charAt(nextY) - 'a'));
                    }
                    if (nextZ == key){
                        return level;
                    }
                    if (!visited[nextX][nextY][nextZ]) {
                        deque.addLast(new int[]{nextX, nextY, nextZ});
                        visited[nextX][nextY][nextZ] = true;
                    }
                }
            }
            level++;
        }
        return -1;
    }

}
