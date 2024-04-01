package leetcode;

public class Path_Sum_112 {


    public boolean hasPathSum(TreeNode root, int targetSum) {
        return dfs(root, targetSum, 0);
    }

    private boolean dfs(TreeNode root, int targetSum, int nodeSum) {
        if (root == null){
            return false;
        }
        if (root.left == null && root.right == null) {
            return targetSum == nodeSum + root.val;
        }

        boolean l = dfs(root.left, targetSum, nodeSum + root.val);
        boolean r = dfs(root.right, targetSum, nodeSum + root.val);
        return l || r;
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
