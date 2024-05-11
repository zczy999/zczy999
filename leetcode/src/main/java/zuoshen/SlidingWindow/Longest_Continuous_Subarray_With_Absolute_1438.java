package zuoshen.SlidingWindow;

import java.util.ArrayDeque;
import java.util.Deque;

public class Longest_Continuous_Subarray_With_Absolute_1438 {

    public int longestSubarray(int[] nums, int limit) {
        Deque<Integer> maxDeque = new ArrayDeque<>();
        Deque<Integer> minDeque = new ArrayDeque<>();

        int res = 0;
        for (int left = 0, right = 0; right < nums.length; right++) {
            while (!maxDeque.isEmpty() && nums[right] > nums[maxDeque.peekLast()]) {
                maxDeque.pollLast();
            }
            maxDeque.addLast(right);

            while (!minDeque.isEmpty() && nums[right] < nums[minDeque.peekLast()]) {
                minDeque.pollLast();
            }
            minDeque.addLast(right);

            int maxLeft = maxDeque.peekFirst();
            int minLeft = minDeque.peekFirst();
            while (nums[maxLeft] - nums[minLeft] > limit) {
                left++;
                if (left > maxLeft) {
                    maxDeque.pollFirst();
                    maxLeft = maxDeque.peekFirst();
                }
                if (left > minLeft) {
                    minDeque.pollFirst();
                    minLeft = minDeque.peekFirst();
                }
            }
            res = Math.max(res, right - left + 1);
        }
        return res;
    }

}
