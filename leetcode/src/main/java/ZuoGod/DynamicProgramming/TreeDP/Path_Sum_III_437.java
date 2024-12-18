package ZuoGod.DynamicProgramming.TreeDP;

import java.util.HashMap;
import java.util.Map;

public class Path_Sum_III_437 {

    int res;
    public int pathSum(TreeNode root, int targetSum) {
        Map<Long, Integer> map = new HashMap<>();
        map.put(0l, 1);
        res = 0;
        dfs(root, targetSum, map, 0l);
        return res;
    }

    private void dfs(TreeNode root, int targetSum, Map<Long, Integer> map,long preSum) {
        if (root == null) {
            return;
        }
        preSum += root.val;
        long diff = preSum - targetSum;
        if (map.containsKey(diff)) {
            res += map.get(diff);
        }
        map.put(preSum, map.getOrDefault(preSum, 0) + 1);
        dfs(root.left, targetSum, map, preSum );
        dfs(root.right, targetSum, map, preSum);
        map.put(preSum, map.get(preSum) - 1);
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

}
