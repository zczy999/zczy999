package leetcode;

public class Best_Time_to_Buy_and_Sell_Stock_II_122 {

    public int maxProfit(int[] prices) {
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
            profit += cur - min;
            min = prices[i];
            cur = prices[i];
        }
        return profit;
    }

    /**
     * 思路跟上面一样 但是简单了很多
     * @param prices
     * @return
     */
    public int maxProfit1(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length ; i++) {
            profit += Math.max(prices[i] - prices[i - 1], 0);
        }
        return profit;
    }

}
