package leetcode;

public class Longest_Increasing_Subsequence_300 {
    public int lengthOfLIS(int[] nums) {
        int len = nums.length;
        int[] dp = new int[len];
        for (int i = 0; i < len; i++) {
            dp[i] = 1;
        }
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int res = dp[0];
        for (int i = 0; i < len; i++) {
            if (dp[i]>res){
                res = dp[i];
            }
        }
        return res;
    }
}
