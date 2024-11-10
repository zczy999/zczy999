package ZuoGod.DynamicProgramming;

import java.util.HashMap;
import java.util.Map;

public class Target_Sum_494 {

    public static void main(String[] args) {
        int[] nums = {7, 9, 3, 8, 0, 2, 4, 8, 3, 9};
        Target_Sum_494 target = new Target_Sum_494();
        int i = target.f3(nums, 0);
        System.out.println(i);
    }

    public int findTargetSumWays(int[] nums, int target) {
        int res = f1(nums, target, 0, 0);
        return res;
    }

    // nums[0...i-1]范围上，已经形成的累加和是sum
    // nums[i...]范围上，每个数字可以标记+或者-
    // 最终形成累加和为target的不同表达式数目
    public int f1(int[] nums, int target, int i, int sum) {
        if (i == nums.length) {
            return sum == target ? 1 : 0;
        }
        return f1(nums, target, i + 1, sum + nums[i]) + f1(nums, target, i + 1, sum - nums[i]);
    }

    /**
     * 严格位置依赖的
     *
     * @param nums
     * @param target
     * @return
     */
    public int f2(int[] nums, int target) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (target > sum || target < -sum) {
            return 0;
        }
        int n = nums.length;
        int[][] dp = new int[n + 1][2 * sum + 1];
        dp[n][target + sum] = 1;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = -sum; j <= sum; j++) {
                if (j + nums[i] + sum <= 2 * sum) {
                    dp[i][j + sum] += dp[i + 1][j + nums[i] + sum];
                }
                if (j - nums[i] + sum >= 0) {
                    dp[i][j + sum] += dp[i + 1][j - nums[i] + sum];
                }
            }
        }
        return dp[0][sum];
    }

    /**
     * 最佳解法 转化为01背包
     *
     * @param nums
     * @param target
     * @return
     */
    public int f3(int[] nums, int target) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        int t = target + sum;
        if (t < 0 || t % 2 != 0) {
            return 0;
        }
        t = t / 2;
        int[][] dp = new int[nums.length + 1][t + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= nums.length; i++) {
            int num = nums[i - 1];
            for (int j = 0; j <= t; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - num >= 0) {
                    dp[i][j] += dp[i - 1][j - num];
                }
            }
        }
        return dp[nums.length][t];
    }


    /**
     * 暴力递归
     */
    private int f1(int[] nums, int target, int i) {

        if (i == nums.length) {
            return target == 0 ? 1 : 0;
        }
        return f1(nums, target - nums[i], i + 1) + f1(nums, target + nums[i], i + 1);
    }

    /**
     * 递归+记忆化搜索版
     */
    private Map<Integer, Map<Integer, Integer>> map = new HashMap<>();

    private int f2(int[] nums, int target, int i) {
        if (i == nums.length) {
            return target == 0 ? 1 : 0;
        }

        if (map.containsKey(i) && map.get(i).containsKey(target)) {
            return map.get(i).get(target);
        }

        int res = f2(nums, target - nums[i], i + 1) + f2(nums, target + nums[i], i + 1);
        map.putIfAbsent(i, new HashMap<>());
        map.get(i).put(target, res);
        return res;
    }

}
