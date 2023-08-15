package leetcode;

public class Jump_Game_II_45 {
    private int[] res;
    private int min = Integer.MAX_VALUE - 1;

    public int jump(int[] nums) {
        res = new int[nums.length];
        return dfs(nums, 0);

    }

    private int dfs(int[] nums, int i) {
        if (i >= nums.length) {
            return 0;
        }
        if (res[i] != 0) {
            return res[i];
        }

        for (int j = i + 1; j < i + nums[i]; j++) {
            min = Math.min(min, dfs(nums, i + j) + 1);
        }

        res[i] = min;
        return min;
    }
}
