package ZuoGod.BinarySearch;

/**
 * LeetCode 875. 爱吃香蕉的珂珂
 *
 * 题目描述：
 * 珂珂喜欢吃香蕉。这里有 n 堆香蕉，第 i 堆中有 piles[i] 根香蕉。
 * 警卫已经离开了，将在 h 小时后回来。
 * 珂珂可以决定她吃香蕉的速度 k（单位：根/小时）。
 * 每个小时，她将会选择一堆香蕉，从中吃掉 k 根。
 * 如果这堆香蕉少于 k 根，她将吃掉这堆的所有香蕉，然后这一小时内不会再吃更多的香蕉。
 * 珂珂喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。
 * 返回她可以在 h 小时内吃掉所有香蕉的最小速度 k（k 为整数）。
 *
 * 解题思路：
 * 1. 使用二分查找在 [1, max(piles)] 区间内找最小速度 k
 * 2. 对于每个速度 k，计算是否能在 h 小时内吃完所有香蕉
 * 3. 如果可以，尝试更小的速度（right = mid - 1）
 * 4. 如果不行，需要更大的速度（left = mid + 1）
 *
 * 关键点：
 * - 每堆香蕉吃完所需时间 = ⌈piles[i] / k⌉ = (piles[i] + k - 1) / k
 * - 注意整数溢出问题，累加时间时要用 long 类型
 *
 * 时间复杂度：O(n * log(max(piles))) - n 是香蕉堆数，每次二分需要遍历所有堆
 * 空间复杂度：O(1) - 只使用常数额外空间
 */
public class Koko_Eating_Bananas_875 {

    /**
     * 计算在 h 小时内吃完所有香蕉的最小速度
     *
     * @param piles 每堆香蕉的数量
     * @param h 可用的小时数
     * @return 最小的吃香蕉速度 k（根/小时）
     */
    public int minEatingSpeed(int[] piles, int h) {
        // 二分查找的右边界：最大堆的香蕉数（最快速度）
        int right = 0;
        for (int i = 0; i < piles.length; i++) {
            right = Math.max(right, piles[i]);
        }

        // 二分查找的左边界：速度至少为 1
        int left = 1;
        // 记录答案
        int ans = right;

        // 二分查找最小速度
        while (left <= right) {
            int mid = left + ((right - left) >> 1);

            // 判断当前速度 mid 是否能在 h 小时内吃完
            if (canEat(piles, mid, h)) {
                // 可以吃完，记录答案，尝试更小的速度
                ans = mid;
                right = mid - 1;
            } else {
                // 吃不完，需要更大的速度
                left = mid + 1;
            }
        }
        return ans;
    }

    /**
     * 判断以速度 k 吃香蕉，能否在 h 小时内吃完所有香蕉
     *
     * @param piles 每堆香蕉的数量
     * @param k 吃香蕉的速度（根/小时）
     * @param h 可用的小时数
     * @return 是否能在 h 小时内吃完
     */
    private boolean canEat(int[] piles, int k, int h) {
        long time = 0;  // 使用 long 避免整数溢出（重要！）
        for (int i = 0; i < piles.length; i++) {
            // 计算吃完这堆香蕉需要的时间（向上取整）
            // piles[i] / k 是商，piles[i] % k != 0 则需要额外 1 小时
            time += piles[i] / k + (piles[i] % k != 0 ? 1 : 0);
        }
        return time <= h;
    }

}
