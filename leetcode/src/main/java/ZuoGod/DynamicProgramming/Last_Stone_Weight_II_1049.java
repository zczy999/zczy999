package ZuoGod.DynamicProgramming;

public class Last_Stone_Weight_II_1049 {

    /**
     * 任意选i块石头，使得他们的重量趋近于总重量的一半，因为这样和另一半抵消的差值就是最小的
     * @param stones
     * @return
     */
    public int lastStoneWeightII(int[] stones) {
        int sum = 0;
        for (int stone : stones) {
            sum += stone;
        }
        int[][] dp = new int[stones.length + 1][sum + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= stones.length; i++) {
            int s = stones[i - 1];
            for (int j = 1; j <= sum; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= s) {
                    dp[i][j] = Math.max(dp[i - 1][j - s], dp[i][j]);
                }
            }
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i <= sum; i++) {
            if (dp[stones.length][i] == 1) {
                int diff = Math.abs(i * 2 - sum);
                if (diff < res) {
                    res = diff;
                }
            }
        }
        return res;
    }

}
