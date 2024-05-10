package zuoshen.MonotonicStack;

import java.util.ArrayDeque;
import java.util.Deque;

public class Maximum_Width_Ramp_962 {

    public static void main(String[] args) {
        Maximum_Width_Ramp_962 maxWidth = new Maximum_Width_Ramp_962();
//        int[] nums = {9, 8, 1, 0, 1, 9, 4, 0, 4, 1};
        int[] nums = {2,2,1};
        System.out.println(maxWidth.maxWidthRamp(nums));
    }

    public int maxWidthRamp(int[] nums) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[stack.peek()]) {
                stack.push(i);
            }
        }
        int max = 0;
        int i = nums.length - 1;

        while (!stack.isEmpty() && i >= stack.peek()) {
            while (!stack.isEmpty() && nums[i] >= nums[stack.peek()]) {
                max = Math.max(max, i - stack.pop());
            }
            i--;
        }

        return max;
    }

}
