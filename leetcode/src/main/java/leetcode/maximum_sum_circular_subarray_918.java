package leetcode;

import java.util.Deque;
import java.util.LinkedList;

public class maximum_sum_circular_subarray_918 {
    public static void main(String[] args) {
        maximum_sum_circular_subarray_918 res = new maximum_sum_circular_subarray_918();
        int[] nums = {-3, -2, -3};
        int i = res.maxSubarraySumCircular(nums);
        System.out.println(i);

    }

    /**
     * 同时获取最大和最小和子数组
     * @param nums
     * @return
     */
    public int maxSubarraySumCircular(int[] nums) {
        int maxend = nums[0];
        int minend = nums[0];
        int max = maxend;
        int min = minend;
        int sum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum += nums[i];
            maxend = Math.max(maxend, 0) + nums[i];
            max = Math.max(maxend, max);
            minend = Math.min(minend + nums[i], nums[i]);
            min = Math.min(minend, min);
        }

        return max < 0 ? max : Math.max(sum - min, max);
    }

    /**
     * 单调队列+滑动窗口+前缀和之差
     *
     * @param nums
     * @return
     */
    public int maxSubarraySumCircular1(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        Deque<int[]> deque = new LinkedList<>();
        deque.offerLast(new int[]{0, 0});
        int len = nums.length;
        int presum = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < 2 * len; i++) {
            while (!deque.isEmpty() && deque.peekFirst()[0] <= (i - len)) {
                deque.pollFirst();
            }
            presum += nums[i % len];
            int[] min = deque.peekFirst();
            if (min != null) {
                max = Math.max(max, presum - min[1]);
            } else {
                max = Math.max(max, presum);
            }
            while (!deque.isEmpty() && presum < deque.peekLast()[1]) {
                deque.pollLast();
            }
            deque.offerLast(new int[]{i + 1, presum});
        }
        return max;
    }

}
