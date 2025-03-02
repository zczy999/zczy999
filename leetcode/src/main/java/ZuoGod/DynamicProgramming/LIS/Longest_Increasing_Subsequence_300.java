package ZuoGod.DynamicProgramming.LIS;

public class Longest_Increasing_Subsequence_300 {

    public static void main(String[] args) {
        Longest_Increasing_Subsequence_300 res = new Longest_Increasing_Subsequence_300();
        int[] nums = {0, 1, 0, 3, 2, 3};
        System.out.println(res.lengthOfLIS(nums));
    }

    /**
     * 计算最长递增子序列的长度
     * 通过动态规划的方式，本方法寻找长度为n的序列中最长递增子序列的长度
     *
     * @param nums 输入的整数数组，代表待分析的序列
     * @return 返回最长递增子序列的长度
     */
    public int lengthOfLIS(int[] nums) {

        int[] end = new int[nums.length];
        int len = 1;
        end[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int rightPos = findRightPos(end, len, nums[i]);
            if (rightPos == -1) {
                end[len++] = nums[i];
            } else {
                end[rightPos] = nums[i];
            }

        }
        return len;
    }

    private int findRightPos(int[] end, int len, int target) {
        int ans = -1;
        int left = 0, right = len - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (end[mid] >= target) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }


    /**
     * 计算给定整数数组的最长递增子序列（LIS）的长度。
     * 使用动态规划方法，时间复杂度为O(n²)。
     *
     * @param nums 输入的整数数组
     * @return 返回数组中最长递增子序列的长度
     */
    public int lengthOfLIS1(int[] nums) {
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
            if (dp[i] > res) {
                res = dp[i];
            }
        }
        return res;
    }
}
