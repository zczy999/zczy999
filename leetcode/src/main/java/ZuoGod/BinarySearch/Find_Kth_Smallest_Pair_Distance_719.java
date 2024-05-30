package ZuoGod.BinarySearch;

import java.util.Arrays;

public class Find_Kth_Smallest_Pair_Distance_719 {

    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        int left = 0, right = nums[nums.length - 1] - nums[0];
        int res = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int k1 = f(mid, nums);
            if (k1 < k) {
                left = mid+1;
            } else {
                res = mid;
                right = mid-1;
            }
        }

        return res;
    }

    private int f(int mid, int[] nums) {
        int sum = 0;
        for (int left = 0, right = 0; right < nums.length; right++) {
            while (nums[right] - nums[left] > mid) {
                left++;
            }
            sum += right - left;
        }
        return sum;
    }

}
