package ZuoGod.DynamicProgramming;

public class Best_Time_to_Buy_and_Sell_Stock_III_123 {

    public int maxProfit(int[] prices) {
        int firstBuy = Integer.MIN_VALUE;
        int firstSell = 0;
        int secondBuy = Integer.MIN_VALUE;
        int secondSell = 0;
        for (int i = 0; i < prices.length; i++) {
            firstBuy = Math.max(firstBuy, -prices[i]);
            firstSell = Math.max(firstSell, firstBuy + prices[i]);
            secondBuy = Math.max(secondBuy, firstSell - prices[i]);
            secondSell = Math.max(secondSell, secondBuy + prices[i]);
        }
        return Math.max(0, Math.max(firstSell, secondSell));
    }


    /**
     * 计算给定股票价格数组中，通过最多两次交易可以获得的最大利润
     * 该方法使用前缀和后缀分解的策略，分别计算每一天之前的最大利润（leftMax）和之后的最大利润（rightMax）
     * 然后遍历数组，找到通过两次交易可以获得的最大利润
     *
     * @param prices 股票每日价格数组
     * @return 最大利润
     */

    public int maxProfit1(int[] prices) {
        int[] leftMax = new int[prices.length];
        for (int i = 1, min = prices[0]; i < prices.length; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], prices[i] - min);
            if (prices[i] < min) {
                min = prices[i];
            }
        }

        int[] rightMax = new int[prices.length];
        for (int i = prices.length - 2, max = prices[prices.length - 1]; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], max - prices[i]);
            if (prices[i] > max) {
                max = prices[i];
            }
        }
        int maxPro = rightMax[0];
        for (int i = 0; i < prices.length - 1; i++) {
            maxPro = Math.max(maxPro, leftMax[i] + rightMax[i + 1]);
        }
        return maxPro;
    }

}
