package ZuoGod.DynamicProgramming;

import java.util.Arrays;

public class Minimum_Cost_to_Cut_a_Stick_1547 {

    public int minCost(int n, int[] cuts) {

        int m = cuts.length;
        int[] arr = new int[m + 2];
        Arrays.sort(cuts);
        arr[0] = 0;
        arr[m + 1] = n;
        for (int i = 1; i <= m; i++) {
            arr[i] = cuts[i - 1];
        }

        int[][] cache = new int[m + 2][m + 2];
        for (int i = 0; i < cache.length; i++) {
            Arrays.fill(cache[i], -1);
        }

        return f1(arr, 1, m, cache);
    }

    private int f1(int[] arr, int l, int r, int[][] cache) {
        if (cache[l][r] != -1) {
            return cache[l][r];
        }
        if (l > r) {
            return 0;
        }
        if (l == r) {
            return arr[l + 1] - arr[l - 1];
        }
        int res = Integer.MAX_VALUE;
        for (int i = l; i <= r; i++) {
            res = Math.min(res, f1(arr, l, i - 1, cache) + f1(arr, i + 1, r, cache) + arr[r + 1] - arr[l - 1]);
        }
        cache[l][r] = res;
        return res;
    }


    /**
     * 错误求解方法
     *
     * @param cuts
     * @param l
     * @param r
     * @return
     */
    private int f(int[] cuts, int l, int r) {
        int ans = 0;
        for (int i = 0; i < cuts.length; i++) {
            if (l < cuts[i] && cuts[i] < r) {
                ans = Math.min(ans, f(cuts, l, cuts[i]) + f(cuts, cuts[i], r) + r - l);
            }
        }
        return ans;

    }

}
