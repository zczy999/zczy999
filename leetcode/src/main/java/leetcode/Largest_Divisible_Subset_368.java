package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Largest_Divisible_Subset_368 {

    public static void main(String[] args) {
        Largest_Divisible_Subset_368 res = new Largest_Divisible_Subset_368();
        int[] nums = {2, 3, 8, 9, 27};
        List<Integer> integers = res.largestDivisibleSubset(nums);
        System.out.println(integers.toString());
    }

    public List<Integer> largestDivisibleSubset(int[] nums) {
        int len = nums.length;
        int[] dp = new int[len];
        int[] pre = new int[len];

        Arrays.fill(dp, 1);
        Arrays.fill(pre, -1);
        Arrays.sort(nums);

        for (int i = 1; i < len; i++) {
            int flag = 0;
            for (int j = i - 1; j >= 0; j--) {
                if (nums[i] % nums[j] == 0 && dp[j] >= flag) {
                    dp[i] = dp[j] + 1;
                    pre[i] = j;
                    flag = dp[j];
                }
            }
        }
        int max = 0;
        int maxIndex = 0;
        for (int i = 0; i < len; i++) {
            if (dp[i] > max) {
                max = dp[i];
                maxIndex = i;
            }
        }
        List<Integer> res = new ArrayList<>();
        while (maxIndex != -1) {
            res.add(nums[maxIndex]);
            maxIndex = pre[maxIndex];
        }
        return res;
    }

    private boolean checkSubset(int j, int i, int[] nums, int[] pre) {
        while (j != -1) {
            if (nums[i] % nums[j] != 0) {
                return false;
            }
            j = pre[j];
        }
        return true;

    }
}
