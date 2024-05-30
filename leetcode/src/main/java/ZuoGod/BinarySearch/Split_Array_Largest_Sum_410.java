package ZuoGod.BinarySearch;

public class Split_Array_Largest_Sum_410 {

    public int splitArray(int[] nums, int k) {
        int max = 0, sum = 0;
        for (int i = 0; i < nums.length; i++) {
            max = Math.max(max, nums[i]);
            sum += nums[i];
        }

        int left = max, right = sum;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            int k1 = f(mid, nums);
            if (k1 <= k) {
                right = mid;
            } else {
                left = mid;
            }
        }
        if (f(left, nums) <= k) {
            return left;
        }
        return right;
    }

    private int f(int k, int[] nums) {
        int sum = 0, res = 1;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum > k) {
                res++;
                sum = nums[i];
            }
        }
        return res;
    }

}
