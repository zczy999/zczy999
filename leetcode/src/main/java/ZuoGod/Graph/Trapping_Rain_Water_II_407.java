package ZuoGod.Graph;

import java.util.PriorityQueue;

public class Trapping_Rain_Water_II_407 {

    public int trapRainWater(int[][] heightMap) {
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int m = heightMap.length;
        int n = heightMap[0].length;
        boolean[][] visited = new boolean[m][n];
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((o1, o2) -> o1[2] - o2[2]);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0 || i == m - 1 || j == n - 1) {
                    priorityQueue.add(new int[]{i, j, heightMap[i][j]});
                    visited[i][j] = true;
                }
            }
        }

        int res = 0;
        while (!priorityQueue.isEmpty()) {
            int[] poll = priorityQueue.poll();
            int x = poll[0], y = poll[1];
            int virtualHeight = poll[2];
            res += virtualHeight - heightMap[x][y];
            for (int[] dir : dirs) {
                int newX = x + dir[0], newY = y + dir[1];
                if (newX < 0 || newX >= m || newY < 0 || newY >= n) continue;
                if (visited[newX][newY]) continue;
                priorityQueue.add(new int[]{newX, newY, Math.max(heightMap[newX][newY], virtualHeight)});
                visited[newX][newY] = true;
            }
        }
        return res;
    }

}
