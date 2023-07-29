package leetcode;

import java.util.Arrays;

public class Remove_Element_27 {
    public static int removeElement(int[] nums, int val) {
        int left = 0;
        int right = 0;
        int res = 0;
        for (; right < nums.length; right++) {
            if (nums[right] == val) {
                res++;
                continue;
            }
            nums[left] = nums[right];
            left++;
        }
        for (int i = nums.length - res; i < nums.length; i++) {
            nums[i] = 0;
        }
        System.out.println(Arrays.toString(nums));
        return nums.length - res;
    }

    public static void main(String[] args) {
        int[] nums = {0, 1, 2, 2, 3, 0, 4, 2};
        Remove_Element_27.removeElement(nums,2);
    }
}
