package ZuoGod.DynamicProgramming;

import java.io.*;

public class bytedance_006 {

    public static int MAXN = 501;

    public static int MAXX = 100001;

    // 对于"一定要买的商品"，直接买！
    // 只把"需要考虑的商品"放入cost、val数组
    public static int[] cost = new int[MAXN];

    public static long[] val = new long[MAXN];


    public static int n, m, x;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        StreamTokenizer in = new StreamTokenizer(br);
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            m = 1;
            in.nextToken();
            x = (int) in.nval;
            long ans = 0;
            long happy = 0;
            for (int i = 0; i < n; i++) {
                in.nextToken();
                int price = (int) in.nval;
                in.nextToken();
                int nowPrice = (int) in.nval;
                in.nextToken();
                int happyPrice = (int) in.nval;
                int discount = price - nowPrice;
                if (discount - nowPrice >= 0) {
                    happy = happy + happyPrice;
                    x = x + discount - nowPrice;
                } else {
                    cost[m] = nowPrice - discount;
                    val[m] = happyPrice;
                    m++;
                }
            }
            out.println(compute() + happy);
            out.flush();
        }

        out.close();
        br.close();
    }


    private static long compute() {
        long[][] dp = new long[m][x + 1];
        for (int i = 1; i < m; i++) {
            for (int j = 1; j <= x; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - cost[i] >= 0) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - cost[i]] + val[i]);
                }
            }
        }
        return dp[m - 1][x];
    }

}
