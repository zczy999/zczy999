package ZuoGod.DynamicProgramming;

import java.io.*;

public class P1064 {

    public static int MAXM = 61;

    public static int[] cost = new int[MAXM];

    public static int[] val = new int[MAXM];

    public static boolean[] king = new boolean[MAXM];

    public static int[] fans = new int[MAXM];

    public static int[][] follows = new int[MAXM][2];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int n = (int) in.nval;
            in.nextToken();
            int m = (int) in.nval;
            for (int i = 1; i <= m; i++) {
                in.nextToken();
                int c = (int) in.nval;
                in.nextToken();
                int v = (int) in.nval;
                in.nextToken();
                int k = (int) in.nval;

                cost[i] = c;
                val[i] = c * v;
                if (k == 0) {
                    king[i] = true;
                } else {
                    follows[k][fans[k]] = i;
                    fans[k]++;
                }
            }
            out.println(compute(n, m));
            out.flush();
        }
        br.close();
        out.close();
    }

    private static int compute(int n, int m) {
        int[][] dp = new int[m + 1][n + 1];

        //上一次主商品编号
        int p = 0;
        for (int i = 1; i <= m; i++) {
            if (!king[i]) {
                continue;
            }
            for (int j = 1; j <= n; j++) {
                dp[i][j] = dp[p][j];
                if (j - cost[i] >= 0) {
                    dp[i][j] = Math.max(dp[i][j], dp[p][j - cost[i]] + val[i]);
                } else {
                    continue;
                }

                // fan1 : 如果有附1商品，编号给fan1，如果没有，fan1 == -1
                // fan2 : 如果有附2商品，编号给fan2，如果没有，fan2 == -1
                int fan1 = fans[i] >= 1 ? follows[i][0] : -1;
                int fan2 = fans[i] >= 2 ? follows[i][1] : -1;
                if (fan1 != -1 && j - cost[i] - cost[fan1] >= 0) {
                    // 可能性3 : 主 + 附1
                    dp[i][j] = Math.max(dp[i][j], dp[p][j - cost[i] - cost[fan1]] + val[i] + val[fan1]);
                }
                if (fan2 != -1 && j - cost[i] - cost[fan2] >= 0) {
                    // 可能性4 : 主 + 附2
                    dp[i][j] = Math.max(dp[i][j], dp[p][j - cost[i] - cost[fan2]] + val[i] + val[fan2]);
                }
                if (fan1 != -1 && fan2 != -1 && j - cost[i] - cost[fan1] - cost[fan2] >= 0) {
                    // 可能性5 : 主 + 附1 + 附2
                    dp[i][j] = Math.max(dp[i][j],
                            dp[p][j - cost[i] - cost[fan1] - cost[fan2]] + val[i] + val[fan1] + val[fan2]);
                }

            }
            p = i;
        }
        return dp[p][n];
    }
}
