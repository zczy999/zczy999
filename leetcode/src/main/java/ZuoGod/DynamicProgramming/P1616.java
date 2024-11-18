package ZuoGod.DynamicProgramming;

import java.io.*;

public class P1616 {

    public static int MAXM = 10001;

    public static int MAXT = 10000001;

    public static int[] cost = new int[MAXM];

    public static int[] val = new int[MAXM];

    public static int t, n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            t = (int) in.nval;
            in.nextToken();
            n = (int) in.nval;
            for (int i = 1; i <= n; i++) {
                in.nextToken();
                cost[i] = (int) in.nval;
                in.nextToken();
                val[i] = (int) in.nval;
            }
            out.println(compute1());
            out.flush();
        }

        out.close();
        br.close();
    }

    public static long compute() {
        long[][] dp = new long[n + 1][t + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= t; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - cost[i] >= 0) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][j - cost[i]] + val[i]);
                }
            }
        }

        return dp[n][t];
    }

    public static long compute1() {
        long[] dp = new long[t + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= t; j++) {
                if (j - cost[i] >= 0) {
                    dp[j] = Math.max(dp[j], dp[j - cost[i]] + val[i]);
                }
            }
        }

        return dp[t];
    }

}
