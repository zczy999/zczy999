package ZuoGod.DynamicProgramming;

import java.io.*;

/**
 * 多重背包通过二进制分组转化成01背包(模版)
 * 宝物筛选
 * 一共有n种货物, 背包容量为t
 * 每种货物的价值(v[i])、重量(w[i])、数量(c[i])都给出
 * 请返回选择货物不超过背包容量的情况下，能得到的最大的价值
 * 测试链接 : https://www.luogu.com.cn/problem/P1776
 * 请同学们务必参考如下代码中关于输入、输出的处理
 * 这是输入输出处理效率很高的写法
 * 提交以下的code，提交时请把类名改成"Main"，可以直接通过
 */
public class P1776 {

    public static int MAXN = 1001;

    public static int[] cost = new int[MAXN];

    public static int[] value = new int[MAXN];

    public static int len, W;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int n = (int) in.nval;
            in.nextToken();
            W = (int) in.nval;
            len = 1;
            for (int i = 1; i <= n; i++) {
                in.nextToken();
                int v = (int) in.nval;
                in.nextToken();
                int c = (int) in.nval;
                in.nextToken();
                int num = (int) in.nval;

                for (int j = 1; num - j >= 0; j = j * 2) {
                    cost[len] = c * j;
                    value[len] = v * j;
                    len++;
                    num = num - j;
                }
                if (num > 0) {
                    cost[len] = c * num;
                    value[len] = v * num;
                    len++;
                }

            }
            out.println(compute());
            out.flush();
        }

        out.close();
        br.close();
    }

    public static int compute() {
        int[] dp = new int[W + 1];
        for (int i = 1; i < len; i++) {
            for (int j = W; j >= cost[i]; j--) {
                if (j - cost[i] >= 0) {
                    dp[j] = Math.max(dp[j], dp[j - cost[i]] + value[i]);
                }
            }
        }
        return dp[W];
    }


}
