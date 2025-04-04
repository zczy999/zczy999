package leetcode;

public class Invert_Binary_Tree_226 {

    public TreeNode invertTree(TreeNode root) {
        f(root);
        return root;
    }

    private void f(TreeNode root) {
        if (root == null){
            return;
        }
        f(root.left);
        f(root.right);
        TreeNode tempLeft = root.left;
        TreeNode tempRight = root.right;
        root.left = tempRight;
        root.right = tempLeft;
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
