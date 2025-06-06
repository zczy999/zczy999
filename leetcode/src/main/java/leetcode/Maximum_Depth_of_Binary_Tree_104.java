package leetcode;

public class Maximum_Depth_of_Binary_Tree_104 {

    public int maxDepth(TreeNode root) {
        return f(root);
    }

    private int f(TreeNode root){
        if (root == null) {
            return 0;
        }
        int left = f(root.left);
        int right = f(root.right);
        return Math.max(left, right) + 1;
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
