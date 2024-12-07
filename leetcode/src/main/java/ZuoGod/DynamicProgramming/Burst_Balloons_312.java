package ZuoGod.DynamicProgramming;

import java.util.Arrays;

public class Burst_Balloons_312 {

    public int maxCoins(int[] nums) {

        int m = nums.length;
        int[] arr = new int[m + 2];
        arr[0] = 1;
        arr[m + 1] = 1;
        for (int i = 1; i <= m; i++) {
            arr[i] = nums[i - 1];
        }
        int[][] cache = new int[m + 2][m + 2];

        return f(arr, 1, m, cache);
    }

    private int f(int[] nums, int l, int r, int[][] cache) {
        if (cache[l][r] != 0) {
            return cache[l][r];
        }
        if (l > r) {
            return 0;
        }
        if (l == r) {
            return nums[l - 1] * nums[l] * nums[r + 1];
        }

        int ans = 0;
        for (int i = l; i <= r; i++) {
            int left = f(nums, l, i - 1, cache);
            int right = f(nums, i + 1, r, cache);
            int cur = nums[i] * nums[l - 1] * nums[r + 1];
            ans = Math.max(ans, left + right + cur);
        }
        cache[l][r] = ans;
        return ans;
    }

}
