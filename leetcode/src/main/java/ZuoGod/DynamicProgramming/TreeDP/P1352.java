package ZuoGod.DynamicProgramming.TreeDP;

// 没有上司的舞会
// 某大学有n个职员，编号为1...n
// 他们之间有从属关系，也就是说他们的关系就像一棵以校长为根的树
// 父结点就是子结点的直接上司
// 现在有个周年庆宴会，宴会每邀请来一个职员都会增加一定的快乐指数
// 但是如果某个职员的直接上司来参加舞会了
// 那么这个职员就无论如何也不肯来参加舞会了
// 所以请你编程计算邀请哪些职员可以使快乐指数最大
// 返回最大的快乐指数。
// 测试链接 : https://www.luogu.com.cn/problem/P1352
// 本题和讲解037的题目7类似
// 链式链接 : https://leetcode.cn/problems/house-robber-iii/
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.*;
import java.util.Arrays;


public class P1352 {

    static int MAXN = 6001;

    //链式前向星建图
    static int[] head = new int[MAXN];
    static int[] next = new int[MAXN];
    static int[] to = new int[MAXN];
    static int cnt;

    static boolean[] boss = new boolean[MAXN];
    static int[] happy = new int[MAXN];

    // 动态规划表
    // no[i] : i为头的整棵树，在i不来的情况下，整棵树能得到的最大快乐值
    public static int[] no = new int[MAXN];
    // no[i] : i为头的整棵树，在i来的情况下，整棵树能得到的最大快乐值
    public static int[] yes = new int[MAXN];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int n = (int) in.nval;
            build(n);
            for (int i = 1; i <= n; i++) {
                in.nextToken();
                happy[i] = (int) in.nval;
            }
            for (int i = 1; i < n; i++) {
                in.nextToken();
                int c = (int) in.nval;
                in.nextToken();
                int p = (int) in.nval;
                addEdge(c, p);
                boss[c] = false;
            }
            int root = 0;
            for (int i = 1; i <= n; i++) {
                if (boss[i]) {
                    root = i;
                    break;
                }
            }
            f(root);
            out.println(Math.max(no[root], yes[root]));
        }
        out.flush();
        out.close();
        br.close();
    }

    private static void f(int root) {
        no[root] = 0;
        yes[root] = happy[root];
        for (int edge = head[root]; edge != 0; edge = next[edge]) {
            int child = to[edge];
            f(child);
            yes[root] += no[child];
            no[root] += Math.max(no[child], yes[child]);
        }

    }

    private static void addEdge(int c, int p) {
        next[cnt] = head[p];
        to[cnt] = c;
        head[p] = cnt++;
    }

    private static void build(int n) {
        cnt = 1;
        Arrays.fill(boss, 1, n + 1, true);
        Arrays.fill(head, 1, n + 1, 0);
    }


}
