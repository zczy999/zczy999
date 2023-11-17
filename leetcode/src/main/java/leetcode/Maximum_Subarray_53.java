package leetcode;

import java.util.Arrays;

public class Maximum_Subarray_53 {

    /**
     * Kadane算法 dp+滚动数组优化
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int maxend = nums[0];
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            maxend = Math.max(maxend, 0) + nums[i];
            max = Math.max(maxend, max);
        }

        return max;
    }

    /**
     * dp
     *
     * @param nums
     * @return
     */
    public int maxSubArray1(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1], 0) + nums[i];
        }
        int max = dp[0];
        for (int i = 1; i < dp.length; i++) {
            if (dp[i] > max) {
                max = dp[i];
            }
        }
        return max;
    }
}
