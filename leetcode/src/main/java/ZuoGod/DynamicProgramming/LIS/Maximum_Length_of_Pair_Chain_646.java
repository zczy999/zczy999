package ZuoGod.DynamicProgramming.LIS;

import java.util.Arrays;

public class Maximum_Length_of_Pair_Chain_646 {

    public static void main(String[] args) {
        Maximum_Length_of_Pair_Chain_646 res = new Maximum_Length_of_Pair_Chain_646();
        int[][] nums = {{1, 2}, {2, 3}, {3, 4}};
        System.out.println(res.findLongestChain(nums));
    }

    public int findLongestChain(int[][] nums) {
        Arrays.sort(nums, (a, b) -> a[0] - b[0]);
        int[] end = new int[nums.length];
        int len = 1;
        end[0] = nums[0][1];
        for (int i = 1; i < nums.length; i++) {
            int rightPos = findRightPos(end, len, nums[i][0]);
            if (rightPos == -1) {
                end[len++] = nums[i][1];
            } else {
                end[rightPos] = Math.min(end[rightPos], nums[i][1]);
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

}
