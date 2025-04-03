package leetcode;

public class Same_Tree_100 {

    public boolean isSameTree(TreeNode p, TreeNode q) {
        return f(p, q);
    }

    private boolean f(TreeNode p, TreeNode q) {
        if (p == null && q == null){
            return true;
        }
        if (p == null || q == null){
            return false;
        }
        if (p.val != q.val){
            return false;
        }
        boolean left = f(p.left, q.left);
        boolean right = f(p.right, q.right);
        return left && right;
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
