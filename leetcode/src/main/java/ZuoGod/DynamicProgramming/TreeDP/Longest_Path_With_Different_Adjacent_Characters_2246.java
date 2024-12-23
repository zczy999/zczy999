package ZuoGod.DynamicProgramming.TreeDP;

import java.util.Arrays;

public class Longest_Path_With_Different_Adjacent_Characters_2246 {

    int MAXN = 100010;
    int[] head = new int[MAXN];
    int[] next = new int[MAXN];
    int[] to = new int[MAXN];
    int cnt;

    public int longestPath(int[] parent, String s) {
        int n = parent.length;
        initGraph(n);
        for (int i = 1; i < n; i++) {
            addEdge(i, parent[i]);
        }
        char[] charArray = s.toCharArray();
        Info res = dfs(charArray, 0);
        return Math.max(res.edgeMax, res.totalMax);
    }

    private Info dfs(char[] charArray, int cur) {
        if (head[cur] == 0) {
            return new Info(1, 1);
        }
        int edgeMax = 0;
        int totalMax = 0;
        for (int i = head[cur]; i != 0; i = next[i]) {
            int next = to[i];
            Info nextInfo = dfs(charArray, next);
            totalMax = Math.max(totalMax, nextInfo.totalMax);
            if (charArray[cur] != charArray[next]) {
                //totalMax要么为子树的totalMax，要么为两个前二大的字数edgeMax之和+1
                totalMax = Math.max(totalMax, edgeMax + nextInfo.edgeMax);
                edgeMax = Math.max(edgeMax, nextInfo.edgeMax + 1);
            } else {
                totalMax = Math.max(totalMax, nextInfo.edgeMax);
                edgeMax = Math.max(edgeMax, 1);
            }
        }
        return new Info(edgeMax, totalMax);
    }

    private void addEdge(int c, int p) {
        next[cnt] = head[p];
        to[cnt] = c;
        head[p] = cnt++;
    }

    private void initGraph(int n) {
        cnt = 1;
    }

    class Info {
        int edgeMax;
        int totalMax;

        public Info(int edgeMax, int totalMax) {
            this.edgeMax = edgeMax;
            this.totalMax = totalMax;
        }
    }
}
