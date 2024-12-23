package ZuoGod.DynamicProgramming.TreeDP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Minimum_Fuel_Cost_to_Report_to_the_Capital_2477 {

    long ans;
    int MAXN = 100010;
    int[] head = new int[MAXN];
    //双向图
    int[] next = new int[2 * MAXN];
    int[] to = new int[2 * MAXN];
    int cnt;

    /**
     * 链式前向星建图
     *
     * @param roads
     * @param seats
     * @return
     */
    public long minimumFuelCost(int[][] roads, int seats) {
        int n = roads.length + 1;
        initGraph(n);
        for (int[] r : roads) {
            addEdge(r[0], r[1]);
            addEdge(r[1], r[0]);
        }
        dfs(seats, 0, -1);
        return ans;
    }

    private int dfs(int seats, int cur, int parent) {
        int childNums = 0;
        for (int i = head[cur]; i != 0; i = next[i]) {
            int child = to[i];
            if (child != parent) {
                int childNum = dfs(seats, child, cur);
                childNums += childNum;
            }
        }
        int total = childNums + 1;
        if (parent != -1) {
            ans += (total + seats - 1) / seats;
        }
        return total;
    }

    private void addEdge(int c, int p) {
        next[cnt] = head[p];
        to[cnt] = c;
        head[p] = cnt++;
    }

    private void initGraph(int n) {
        cnt = 1;
        Arrays.fill(head, 0, n, 0);

    }


    /**
     * 使用链接表建图
     *
     * @param roads
     * @param seats
     * @return
     */
    public long minimumFuelCost1(int[][] roads, int seats) {
        int n = roads.length + 1;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] r : roads) {
            graph.get(r[0]).add(r[1]);
            graph.get(r[1]).add(r[0]);
        }
        ans = 0;
        dfs(graph, seats, 0, -1);
        return ans;
    }

    /**
     * 返回当前节点的子树的个数（包括自己）
     *
     * @param graph
     * @param seats
     * @param cur
     * @param parent
     * @return
     */
    private int dfs(List<List<Integer>> graph, int seats, int cur, int parent) {
        int childNums = 0;
        for (int next : graph.get(cur)) {
            if (next == parent) {
                continue;
            }
            int childNum = dfs(graph, seats, next, cur);
            childNums += childNum;
        }
        int total = childNums + 1;
        if (parent != -1) {
            ans += (total + seats - 1) / seats;
        }

        return total;
    }


}
