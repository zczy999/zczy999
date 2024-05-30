package ZuoGod.BFS;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Minimum_Obstacle_Removal_to_Reach_Corner_2290 {

    public int minimumObstacles(int[][] grid) {
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int len = grid[0].length;
        int aim = grid.length * len - 1;
        int[] distance = new int[aim + 1];
        Arrays.fill(distance, Integer.MAX_VALUE);

        Deque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(0);
        distance[0] = 0;
        while (!deque.isEmpty()) {
            int cur = deque.pollFirst();
            if (cur == aim) {
                return distance[cur];
            }
            int row = cur / len;
            int col = cur % len;
            for (int[] dir : dirs) {
                int curX = row + dir[0];
                int curY = col + dir[1];
                if (curX < 0 || curX >= grid.length || curY < 0 || curY >= len) {
                    continue;
                }
                int num = curX * len + curY;
                if (distance[num] > distance[cur] + grid[curX][curY]) {
                    distance[num] = distance[cur] + grid[curX][curY];
                    if (grid[curX][curY] == 0) {
                        deque.addFirst(curX * len + curY);
                    } else {
                        deque.addLast(curX * len + curY);
                    }
                }
            }
        }
        return -1;
    }

}
