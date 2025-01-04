package ZuoGod.DynamicProgramming.BitmaskDP;

import java.io.*;

public class P1171_TSP {

    public static int MAXN = 19;

    public static int[][] graph = new int[MAXN][MAXN];

    public static int[][] dp = new int[1 << MAXN][MAXN];

    public static int n;

    public static void build() {
        for (int s = 0; s < (1 << n); s++) {
            for (int i = 0; i < n; i++) {
                dp[s][i] = -1;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            build();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    in.nextToken();
                    graph[i][j] = (int) in.nval;
                }
            }
            out.println(compute());
        }
        out.flush();
        out.close();
        br.close();
    }

    static int compute() {
        return f(1, 0);
    }

    static int f(int status, int cur) {
        if (status == (1 << n) - 1) {
            return graph[cur][0];
        }
        if (dp[status][cur] != -1){
            return dp[status][cur];
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if ((status & (1 << i)) == 0) {
                ans = Math.min(ans, f(status | (1 << i), i) + graph[cur][i]);
            }
        }
        dp[status][cur] = ans;
        return ans;
    }

}
