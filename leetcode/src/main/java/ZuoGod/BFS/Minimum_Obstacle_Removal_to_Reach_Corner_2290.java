package ZuoGod.BFS;

import utils.LeetcodeInput;

import java.util.*;

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

    public static void main(String[] args) {
        String json = "[[0,1,1],[1,1,0],[1,1,0]]";
        int[][] grid = LeetcodeInput.fromJson(json, int[][].class);
        Minimum_Obstacle_Removal_to_Reach_Corner_2290 res = new Minimum_Obstacle_Removal_to_Reach_Corner_2290();
        res.minimumObstacles1(grid).stream().map(is -> {
            String str = "";
            for(int i : is){
                str = str + i + " ";
            }
            return str;
        }).forEach(System.out::println);
    }

    public List<int[]> minimumObstacles1(int[][] grid) {
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int len = grid[0].length;
        int aim = grid.length * len - 1;
        int[] distance = new int[aim + 1];
        Arrays.fill(distance, Integer.MAX_VALUE);

        Deque<PathNode> deque = new ArrayDeque<>();
        deque.addFirst(new PathNode(0, 0, 0, new ArrayList<>()));
        distance[0] = 0;

        while (!deque.isEmpty()) {
            PathNode cur = deque.pollFirst();
            int curIdx = cur.x * len + cur.y;
            if (curIdx == aim) {
                return cur.obstacles;
            }

            for (int[] dir : dirs) {
                int curX = cur.x + dir[0];
                int curY = cur.y + dir[1];
                if (curX < 0 || curX >= grid.length || curY < 0 || curY >= len) {
                    continue;
                }

                int num = curX * len + curY;
                int newDistance = distance[curIdx] + grid[curX][curY];
                if (newDistance < distance[num]) {
                    distance[num] = newDistance;
                    List<int[]> newObstacles = new ArrayList<>(cur.obstacles);
                    if (grid[curX][curY] == 1) {
                        newObstacles.add(new int[]{curX, curY});
                    }
                    if (grid[curX][curY] == 0) {
                        deque.addFirst(new PathNode(curX, curY, newDistance, newObstacles));
                    } else {
                        deque.addLast(new PathNode(curX, curY, newDistance, newObstacles));
                    }
                }
            }
        }
        return new ArrayList<>(); // 如果不能到达目标，返回空列表
    }

    private static class PathNode {
        int x, y, dist;
        List<int[]> obstacles;

        PathNode(int x, int y, int dist, List<int[]> obstacles) {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.obstacles = obstacles;
        }
    }


}
