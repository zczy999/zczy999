package lintcode;

import java.util.*;

public class ModernLudoI_1565 {

    /**
     * @param length:      the length of board
     * @param connections: the connections of the positions
     * @return: the minimum steps to reach the end
     */
    public int modernLudo(int length, int[][] connections) {
        // Write your code here
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> distance = new HashMap<>();
        //初始节点
        distance.put(1, 0);
        //构造图
        createGraph(length, graph, connections);

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(1);
        while (!queue.isEmpty()) {
            int n = queue.poll();
            //一次投骰子能够到达的点
            int len = Math.min(n + 6, length);
            for (int i = n; i <= len; i++) {
                if (distance.containsKey(i)) {
                    continue;
                }
                for (int j : getGraphNode(i, graph, distance)) {
                    distance.put(j, distance.get(n) + 1);
                    queue.offer(j);
                }
            }
        }


        return distance.get(length);
    }

    private List<Integer> getGraphNode(int i, Map<Integer, Set<Integer>> graph, Map<Integer, Integer> distance) {
        Set<Integer> set = graph.get(i);
        List<Integer> res = new ArrayList<>();
        Queue<Integer> queue = new ArrayDeque<>();
        res.add(i);
        queue.offer(i);

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            if (distance.containsKey(cur)) {
                continue;
            }
            for (int neighbour : graph.get(cur)) {
                if (distance.containsKey(neighbour)) {
                    continue;
                }
                res.add(neighbour);
                queue.offer(neighbour);
            }
        }

        return res;
    }

    private void createGraph(int length, Map<Integer, Set<Integer>> graph, int[][] connections) {
        for (int i = 1; i <= length; i++) {
            graph.put(i, new HashSet<Integer>());
        }
        for (int[] connection : connections) {
            int from = connection[0];
            int to = connection[1];
            graph.get(from).add(to);
        }
    }
}
