package ZuoGod.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class LCP35_VisitCityMinCost {

    public int electricCarPlan(int[][] paths, int cnt, int start, int end, int[] charge) {
        //建图
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < charge.length; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] path : paths) {
            graph.get(path[0]).add(new int[]{path[1], path[2]});
            graph.get(path[1]).add(new int[]{path[0], path[2]});
        }

        int[][] distance = new int[charge.length][cnt + 1];
        for (int i = 0; i < charge.length; i++) {
            for (int j = 0; j < cnt + 1; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }

        boolean[][] visited = new boolean[charge.length][cnt + 1];
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[2] - o2[2]);
        pq.add(new int[]{start, 0, 0});
        distance[start][0] = 0;
        while (!pq.isEmpty()) {
            int[] record = pq.poll();
            int cur = record[0];
            int power = record[1];
            //这里cost即是distance
            int cost = record[2];
            if (visited[cur][power]) {
                continue;
            }
            if (cur == end) {
                return cost;
            }
            //弹出堆表示这节点最短路找到
            visited[cur][power] = true;

            //电池没满，原地充电！
            if (power < cnt) {
                //充电节点要没弹出堆过，且代价要小，进堆
                if (!visited[cur][power + 1] && cost + charge[cur] < distance[cur][power + 1]) {
                    distance[cur][power + 1] = cost + charge[cur];
                    pq.add(new int[]{cur, power + 1, cost + charge[cur]});
                }
            }

            //找其他没有弹出过堆的城市
            for (int[] neighbor : graph.get(cur)) {
                // 不充电去别的城市
                int nextCity = neighbor[0];
                int restPower = power - neighbor[1];
                int nextCost = cost + neighbor[1];
                //电量到达不了或者弹出过该节点
                if (restPower < 0 || visited[nextCity][restPower]) {
                    continue;
                }
                //代价小进堆
                if (nextCost < distance[nextCity][restPower]) {
                    distance[nextCity][restPower] = nextCost;
                    pq.add(new int[]{nextCity, restPower, nextCost});
                }
            }
        }
        return -1;
    }
}


