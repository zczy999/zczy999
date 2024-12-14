package ZuoGod.DynamicProgramming.TreeDP;


public class Maximum_Sum_BST_in_Binary_Tree_1373 {

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

    public class Info {
        public int max;
        public int min;
        public boolean isBst;
        public int sum;
        public int maximumSum;

        public Info(int a, int b, boolean c, int d, int e) {
            max = a;
            min = b;
            isBst = c;
            sum = d;
            maximumSum = e;
        }
    }

    public int maxSumBST(TreeNode root) {
        return f(root).maximumSum;
    }

    private Info f(TreeNode x) {
        if (x == null) {
            return new Info(Integer.MIN_VALUE, Integer.MAX_VALUE, true, 0, 0);
        }

        Info infol = f(x.left);
        Info infor = f(x.right);
        int max = Math.max(x.val, Math.max(infol.max, infor.max));
        int min = Math.min(x.val, Math.min(infol.min, infor.min));
        int sum = infol.sum + infor.sum + x.val;
        boolean isBst = infol.isBst && infor.isBst && infol.max < x.val && x.val < infor.min;
        int maxBstSum = Math.max(infol.maximumSum, infor.maximumSum);
        if (isBst) {
            maxBstSum = Math.max(maxBstSum, sum);
        }
        return new Info(max, min, isBst, sum, maxBstSum);

    }


}
