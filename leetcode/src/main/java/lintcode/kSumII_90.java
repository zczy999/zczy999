package lintcode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数组 = [1,3,4,6]
 * k = 3
 * target = 8
 * ans = [[1,3,4]]
 */
public class kSumII_90 {
    private List<List<Integer>> lists = new ArrayList<>();
    private int target;

    private int k;

    public List<List<Integer>> kSumII(int[] nums, int k, int target) {
        this.target = target;
        this.k = k;
        int sum = 0;
        List<Integer> list = new ArrayList<>();
        dfs(nums, 0, sum, list);

        return lists;
    }

    private void dfs(int[] nums, int index, int sum, List<Integer> list) {
        if (sum == target && list.size() == k) {
            lists.add(new ArrayList<>(list));
            return;
        }
        if (list.size() > k) {
            return;
        }
        for (; index < nums.length; index++) {
            list.add(nums[index]);
            dfs(nums, index + 1, sum + nums[index], list);
            list.remove(list.size() - 1);
        }


    }

    public static void main(String[] args) {
        kSumII_90 kSumII_90 = new kSumII_90();
        int[] nums = {1, 3, 4, 6};
        List<List<Integer>> list = kSumII_90.kSumII(nums, 3, 8);
        System.out.println(list);
    }
}
