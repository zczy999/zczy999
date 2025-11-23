package leetcode;

import java.util.Arrays;

/**
 * 135. 分发糖果
 *
 * 题目描述：
 * n 个孩子站成一排。给你一个整数数组 ratings 表示每个孩子的评分。
 * 你需要按照以下要求，给这些孩子分发糖果：
 * 1. 每个孩子至少分配到 1 个糖果
 * 2. 相邻两个孩子评分更高的孩子会获得更多的糖果
 * 请你给每个孩子分发糖果，计算并返回需要准备的最少糖果数目。
 *
 * 提供两种解法：
 * 1. 两次遍历法（推荐）：时间 O(n)，空间 O(n)，逻辑清晰易懂
 * 2. 一次遍历法（优化）：时间 O(n)，空间 O(1)，空间最优但逻辑复杂
 */
public class Candy_135 {
    public static void main(String[] args) {
        int[] ratings = {1, 0, 2};

        // 测试两种解法
        int result1 = Candy_135.candy(ratings);
        int result2 = Candy_135.candyTwoPass(ratings);

        System.out.println("一次遍历法结果: " + result1);
        System.out.println("两次遍历法结果: " + result2);
    }

    /**
     * 解法一：两次遍历法（推荐，逻辑清晰）
     *
     * 核心思路：
     * 1. 第一次从左到右遍历，保证如果右边评分高，则糖果比左边多
     * 2. 第二次从右到左遍历，保证如果左边评分高，则糖果比右边多
     * 3. 两次遍历后，每个位置的糖果数同时满足左右两个约束条件
     *
     * @param ratings 每个孩子的评分数组
     * @return 最少糖果总数
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    public static int candyTwoPass(int[] ratings) {
        int n = ratings.length;
        if (n == 0) return 0;

        // 初始化：每个孩子至少得到 1 个糖果
        int[] candies = new int[n];
        Arrays.fill(candies, 1);

        // 第一次遍历：从左到右，保证右边评分高的糖果比左边多
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }

        // 第二次遍历：从右到左，保证左边评分高的糖果比右边多
        // 注意：这里要用 max，因为可能已经满足条件了
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }

        // 累加总糖果数
        int total = 0;
        for (int candy : candies) {
            total += candy;
        }
        return total;
    }

    /**
     * 解法二：一次遍历法（空间优化）
     *
     * 核心思路：
     * 通过一次遍历，分别记录上升序列和下降序列的糖果数
     * - 上升序列：糖果数依次递增 (1, 2, 3, ...)
     * - 下降序列：糖果数依次递增 (1, 2, 3, ...)，但如果下降长度超过上升长度，
     *   需要给峰值多加一个糖果
     * - 相等评分：重置为最小值 1
     *
     * @param ratings 每个孩子的评分数组
     * @return 最少糖果总数
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public static int candy(int[] ratings) {
        int ascendingCandies = 1;   // 上升序列中当前位置应获得的糖果数
        int descendingCandies = 0;  // 下降序列中当前位置应获得的糖果数
        int totalCandies = 1;       // 糖果总数，第一个孩子初始为 1 个糖果

        // 从第二个孩子开始遍历
        for (int i = 1; i < ratings.length; i++) {
            // 情况1：评分上升，糖果数递增
            if (ratings[i] > ratings[i - 1]) {
                // 如果之前是下降序列，现在转为上升，重置上升糖果数为 1
                if (descendingCandies != 0) {
                    ascendingCandies = 1;
                }
                descendingCandies = 0;         // 清空下降序列
                ascendingCandies++;            // 上升序列糖果数加 1
                totalCandies += ascendingCandies;  // 累加当前孩子的糖果数

            // 情况2：评分相等，糖果数重置为 1
            } else if (ratings[i] == ratings[i - 1]) {
                ascendingCandies = 0;          // 清空上升序列（下次上升时会重置）
                descendingCandies = 1;         // 重置为最小值
                totalCandies += descendingCandies; // 当前孩子获得 1 个糖果

            // 情况3：评分下降，糖果数递增（从下降序列角度看）
            } else {
                descendingCandies++;           // 下降序列糖果数加 1
                // 关键优化：如果下降序列的糖果数等于上升序列的峰值
                // 说明下降序列已经"追上"了峰值，需要给峰值多加一个糖果
                if (ascendingCandies == descendingCandies) {
                    descendingCandies++;
                }
                totalCandies += descendingCandies; // 累加当前孩子的糖果数
            }
        }
        return totalCandies;
    }
}
