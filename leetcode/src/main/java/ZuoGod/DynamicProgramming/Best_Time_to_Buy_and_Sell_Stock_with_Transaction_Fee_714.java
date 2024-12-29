package ZuoGod.DynamicProgramming;

public class Best_Time_to_Buy_and_Sell_Stock_with_Transaction_Fee_714 {

    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < n; ++i) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i] - fee);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[n - 1][0];
    }



    /**
     * 错误解法
     * @param prices
     * @param fee
     * @return
     */
    public int maxProfitF(int[] prices, int fee) {
        int min = prices[0];
        int cur = prices[0];
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] >= cur) {
                cur = prices[i];
                if (i != prices.length - 1) {
                    continue;
                }
            }
            if (cur - min - fee > 0) {
                profit += cur - min - fee;
                min = prices[i];
                cur = prices[i];
            }

        }
        return profit;

    }


}
