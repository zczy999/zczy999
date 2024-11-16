package ZuoGod.DynamicProgramming;

import java.util.ArrayList;
import java.util.List;

public class Maximum_Value_of_K_Coins_From_Piles_2218 {

    public int maxValueOfCoins(List<List<Integer>> piles, int k) {
        List<List<Integer>> preSum = new ArrayList<>();
        //保证从1开始
        preSum.add(new ArrayList<>());
        for (int i = 0; i < piles.size(); i++) {
            int sum = 0;
            preSum.add(new ArrayList<>());
            preSum.get(i + 1).add(0);
            for (int j = 0; j < piles.get(i).size(); j++) {
                sum += piles.get(i).get(j);
                preSum.get(i + 1).add(sum);
            }
        }

        int[][] dp = new int[preSum.size()][k + 1];
        for (int i = 1; i < preSum.size(); i++) {
            for (int j = 0; j <= k; j++) {
                dp[i][j] = dp[i - 1][j];
                for (int l = 1; l < preSum.get(i).size(); l++) {
                    if (j - l >= 0) {
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - l] + preSum.get(i).get(l));
                    }
                }
            }
        }
        return dp[preSum.size() - 1][k];
    }

}
