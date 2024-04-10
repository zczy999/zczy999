package zuoshen.DynamicProgramming;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Ugly_NumberII_264 {

    public int nthUglyNumber(int n) {
        int[] dirs = {2, 3, 5};
        Set<Long> set = new HashSet<>();
        PriorityQueue<Long> pq = new PriorityQueue<>();
        pq.add(1L);
        set.add(1L);
        int res = 0;
        for (int i = 1; i <= n; i++) {
            long poll = pq.poll();
            for (int dir : dirs) {
                if (set.add(dir * poll)) {
                    pq.add(dir * poll);
                }
            }
            res =(int) poll;
        }
        return res;
    }

    /**
     * 动态规划解法
     *
     * @param n
     * @return
     */
    public int nthUglyNumber1(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        int n1 = 1, n2 = n1, n3 = n2;
        for (int i = 2; i <= n; i++) {
            dp[i] = Math.min(2 * dp[n1], Math.min(3 * dp[n2], 5 * dp[n3]));
            if (2 * dp[n1] == dp[i]) {
                n1++;
            }
            if (3 * dp[n2] == dp[i]) {
                n2++;
            }
            if (5 * dp[n3] == dp[i]) {
                n3++;
            }
        }
        return dp[n];
    }

}
