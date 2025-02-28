package ZuoGod.DynamicProgramming;

public class House_Robber_IV_2560 {

    public int minCapability(int[] nums, int k) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < min) {
                min = nums[i];
            }
            if (nums[i] > max) {
                max = nums[i];
            }
        }
        int left = min;
        int right = max;
        int res = max;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (count(nums, mid) >= k){
                res = mid;
                right = mid - 1;
            }else {
                left = mid + 1;
            }
        }
        return res;
    }

    private int count(int[] nums, int v) {
        if (nums.length == 1){
            return nums[0] <= v ? 1 : 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0] <= v ? 1 : 0;
        dp[1] = nums[1] <= v ? 1 : dp[0];
        int max = Math.max(dp[0], dp[1]);
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] > v){
                dp[i] = dp[i - 1];
            }else {
                dp[i] = Math.max(dp[i - 1], dp[i - 2] + 1);
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
