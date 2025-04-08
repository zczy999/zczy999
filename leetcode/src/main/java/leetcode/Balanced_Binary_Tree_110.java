package leetcode;

public class Balanced_Binary_Tree_110 {

    boolean res;
    public boolean isBalanced(TreeNode root) {
        res = true;
        height(root);
        return res;
    }

    private int height(TreeNode root) {
        if (root == null){
            return 0;
        }
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            res = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;
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
