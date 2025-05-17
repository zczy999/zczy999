package leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 */
public class Permutations_46 {

    List<List<Integer>> res;

    public List<List<Integer>> permute(int[] nums) {
        this.res = new ArrayList<>();
        int[] flag = new int[nums.length];
        dfs(nums, 0, flag, new ArrayList<Integer>());

        return res;
    }

    private void dfs(int[] nums, int i, int[] flag, ArrayList<Integer> list) {
        if (i == nums.length) {
            res.add(new ArrayList<>(list));
        }
        for (int j = 0; j < nums.length; j++) {
            if (flag[j] == 0) {
                list.add(nums[j]);
                flag[j] = 1;
                dfs(nums, i + 1, flag, list);
                list.remove(list.size() - 1);
                flag[j] = 0;
            }
        }
    }


}
