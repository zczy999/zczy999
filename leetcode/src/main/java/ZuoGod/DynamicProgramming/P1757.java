package ZuoGod.DynamicProgramming;

import java.io.*;
import java.util.Arrays;

public class P1757 {

    public static int MAXN = 1001;

    public static int MAXM = 1001;

    // arr[i][0] i号物品的体积
    // arr[i][1] i号物品的价值
    // arr[i][2] i号物品的组号
    public static int[][] arr = new int[MAXN][3];

    public static int[] dp = new int[MAXM];

    public static int m, n;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            m = (int) in.nval;
            in.nextToken();
            n = (int) in.nval;

            for (int i = 1; i <= n; i++) {
                in.nextToken();
                int cost = (int) in.nval;
                in.nextToken();
                int value = (int) in.nval;
                in.nextToken();
                int group = (int) in.nval;
                arr[i] = new int[]{cost, value, group};
            }
            //按组号排序
            Arrays.sort(arr, 1, n + 1, ((o1, o2) -> o1[2] - o2[2]));
            out.println(compute());
            out.flush();
        }
        out.close();
        br.close();
    }

    public static int compute() {
        int group = 1;
        for (int i = 2; i <= n; i++) {
            if (arr[i - 1][2] != arr[i][2]) {
                group++;
            }
        }


        int[][] dp = new int[group + 1][m + 1];
        for (int i = 1, start = 1, end = 1; i <= group; i++) {
            while (end <= n && arr[start][2] == arr[end][2]) {
                end++;
            }


            for (int j = 0; j <= m; j++) {
                dp[i][j] = dp[i - 1][j];
                for (int k = start; k < end; k++) {
                    if (j - arr[k][0] >= 0) {
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - arr[k][0]] + arr[k][1]);
                    }
                }
            }

            start = end;
        }

        return dp[group][m];
    }


}
