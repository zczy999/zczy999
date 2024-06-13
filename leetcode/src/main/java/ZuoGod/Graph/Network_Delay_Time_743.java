package ZuoGod.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Network_Delay_Time_743 {

    public int networkDelayTime(int[][] times, int n, int k) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] time : times) {
            int start = time[0];
            int end = time[1];
            int weight = time[2];
            graph.get(start).add(new int[]{end, weight});
        }

        boolean[] visited = new boolean[n + 1];
        int[] distance = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            distance[i] = Integer.MAX_VALUE;
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
        pq.add(new int[]{k, 0});
        distance[k] = 0;
        while (!pq.isEmpty()) {
            int[] net = pq.poll();
            int curNet = net[0];
            int delayTime = net[1];
            if (visited[curNet]) {
                continue;
            }
            visited[curNet] = true;
            for (int[] edge : graph.get(curNet)) {
                int nextNet = edge[0];
                int nextDelayTime = edge[1] + delayTime;
                if (!visited[nextNet] && nextDelayTime < distance[nextNet]) {
                    distance[nextNet] = nextDelayTime;
                    pq.add(new int[]{nextNet, nextDelayTime});
                }
            }
        }
        int res = 0;
        for (int i = 1; i <= n; i++) {
            if (distance[i] == Integer.MAX_VALUE) {
                return -1;
            }
            if (res < distance[i]) {
                res = distance[i];
            }
        }
        return res;
    }
}

