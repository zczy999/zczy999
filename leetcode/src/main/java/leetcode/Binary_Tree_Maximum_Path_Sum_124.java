package leetcode;

public class Binary_Tree_Maximum_Path_Sum_124 {

    int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        getMaxPathSum(root);
        return maxSum;
    }

    /**
     * 当前节点的最大路径： max(自己，自己+左边，自己+右边，自己 + 左边 + 右边）
     * 当前节点作为子节点时的贡献：max(自己，自己+左边，自己+右边）
     * @param root
     * @return
     */
    private int getMaxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftMaxSum = getMaxPathSum(root.left);
        int rightMaxSum = getMaxPathSum(root.right);
        int res = Math.max(root.val, root.val + leftMaxSum);
        res = Math.max(res, root.val + rightMaxSum);

        int res1 = Math.max(res, root.val + leftMaxSum + rightMaxSum);
        maxSum = Math.max(Math.max(maxSum, res1),res);

        return res;
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
