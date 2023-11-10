package leetcode;

import java.util.*;

public class Course_Schedule_207 {
    public static void main(String[] args) {
        Course_Schedule_207 res = new Course_Schedule_207();
        int[][] prerequisites = {{1, 4}, {2, 4}, {3, 1}, {3, 2}};
        boolean b = res.canFinish(5, prerequisites);
        System.out.println(b);
    }

    /**
     * dfs解决
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] flag = new int[numCourses];
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < prerequisites.length; i++) {
            int[] cur = prerequisites[i];
            graph.computeIfAbsent(cur[1], k -> new ArrayList<>()).add(cur[0]);
        }
        for (int i = 0; i < numCourses; i++) {
            if (!dfs(i, graph, flag)) {
                return false;
            }
        }
        return true;

    }

    private boolean dfs(int i, Map<Integer, List<Integer>> graph, int[] flag) {
        //有向图会有三种状态
        //未被 DFS 访问：i == 0；
        //已被其他节点启动的 DFS 访问：i == -1；
        //已被当前节点启动的 DFS 访问：i == 1。
        if (flag[i] == 1) {
            return false;
        }
        if (flag[i] == -1 || !graph.containsKey(i)) {
            return true;
        }
        flag[i] = 1;
        for (Integer cur : graph.get(i)) {
            if (!dfs(cur, graph, flag)) {
                return false;
            }
        }
        flag[i] = -1;
        return true;
    }


    /**
     * bfs + 判断节点入度
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinish1(int numCourses, int[][] prerequisites) {
        int[] indeg = new int[numCourses];
        Set<Integer> set = new HashSet<>();
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < prerequisites.length; i++) {
            int[] cur = prerequisites[i];
            //初始化入度
            indeg[cur[0]]++;
            graph.computeIfAbsent(cur[1], k -> new ArrayList<>()).add(cur[0]);
        }
        List<Integer> lists = findIndeg(indeg, 0);
        if (lists.isEmpty()) {
            return false;
        }
        Queue<Integer> queue = new LinkedList<>();
        for (Integer i : lists) {
            set.add(i);
            queue.add(i);
        }
        while (!queue.isEmpty()) {
            Integer cur = queue.poll();
            List<Integer> next = graph.get(cur);
            if (next == null) {
                continue;
            }
            for (Integer i : next) {
                indeg[i]--;
            }
            List<Integer> list = findIndeg(indeg, 0);
            for (Integer i : list) {
                if (set.add(i)) {
                    queue.add(i);
                }
            }
        }
        for (int i : indeg) {
            if (i != 0) {
                return false;
            }
        }
        return true;

    }

    private List<Integer> findIndeg(int[] indeg, int num) {
        List<Integer> res = new ArrayList<>();
        for (int j = 0; j < indeg.length; j++) {
            if (indeg[j] == num) {
                res.add(j);
            }
        }
        return res;
    }

    /**
     * 错误解法，这是有向图
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinishf(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < prerequisites.length; i++) {
            int[] cur = prerequisites[i];
            graph.computeIfAbsent(cur[1], k -> new ArrayList<>()).add(cur[0]);
        }
        for (int i = 0; i < numCourses; i++) {
            HashSet<Integer> flag = new HashSet<>();
            if (!dfsf(i, graph, flag)) {
                return false;
            }
        }
        return true;
    }

    private boolean dfsf(int i, Map<Integer, List<Integer>> graph, HashSet<Integer> flag) {
        if (!flag.add(i)) {
            return false;
        }
        if (!graph.containsKey(i)) {
            return true;
        }
        for (Integer cur : graph.get(i)) {
            if (!dfsf(cur, graph, flag)) {
                return false;
            }
        }
        return true;
    }
}
