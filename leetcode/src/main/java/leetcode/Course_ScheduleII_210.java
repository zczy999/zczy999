package leetcode;

import java.util.*;

public class Course_ScheduleII_210 {

    public static void main(String[] args) {
        int[][] prerequisites = {{1, 0}};
        Course_ScheduleII_210 res = new Course_ScheduleII_210();
        int[] order = res.findOrder(2, prerequisites);
        System.out.println(Arrays.toString(order));
    }
    int[] res;
    int index;
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] flag = new int[numCourses];
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < numCourses; i++) {
            List<Integer> list = new ArrayList<>();
            graph.put(i,list);
        }
        for (int i = 0; i < prerequisites.length; i++) {
            int[] cur = prerequisites[i];
            graph.get(cur[1]).add(cur[0]);
        }
        res = new int[numCourses];
        index = numCourses - 1;
        for (int i = 0; i < numCourses; i++) {
            if (!dfs(i, graph, flag)) {
                return new int[0];
            }
        }
        return res;

    }

    private boolean dfs(int i, Map<Integer, List<Integer>> graph, int[] flag) {
        //有向图会有三种状态
        //未被 DFS 访问：i == 0；
        //已被其他节点启动的 DFS 访问：i == -1；
        //已被当前节点启动的 DFS 访问：i == 1。
        if (flag[i] == 1) {
            return false;
        }
        if (flag[i] == -1) {
            return true;
        }

        flag[i] = 1;
        for (Integer cur : graph.get(i)) {
            if (!dfs(cur, graph, flag)) {
                return false;
            }
        }
        flag[i] = -1;
        res[index--] = i;
        return true;
    }
}
