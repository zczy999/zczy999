package leetcode;

import java.util.Arrays;

public class Remove_Duplicates_from_Sorted_Array_26 {
    public static int removeDuplicates(int[] nums) {
        if (nums.length == 1) {
            return 1;
        }
        int res = 0;
        int left = 1;
        for (int right = 1; right < nums.length; right++) {
            if (nums[right] == nums[right - 1]) {
                res++;
                continue;
            }
            nums[left] = nums[right];
            left++;
        }
        System.out.println(Arrays.toString(nums));
        return nums.length - res;
    }

    public static void main(String[] args) {
        int[] nums = {1, 1, 2};
        Remove_Duplicates_from_Sorted_Array_26.removeDuplicates(nums);

    }
}
