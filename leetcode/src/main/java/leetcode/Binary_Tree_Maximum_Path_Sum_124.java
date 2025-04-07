package leetcode;

public class Binary_Tree_Maximum_Path_Sum_124 {

    int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        int maxPathSum = getMaxPathSum(root);
        return maxSum;
    }

    /**
     * 从当前节点 node 出发，向下延伸所能获得的最大路径和
     *
     * @param root
     * @return
     */
    private int getMaxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            //如果树只有一个节点，maxSum需要被更新
            maxSum = Math.max(maxSum, root.val);
            return root.val;
        }

        //如果子节点为负 则不选择 为0
        int leftMaxSum = Math.max(getMaxPathSum(root.left), 0) + root.val;
        int rightMaxSum = Math.max(getMaxPathSum(root.right), 0) + root.val;

        maxSum = Math.max(maxSum, leftMaxSum + rightMaxSum - root.val);

        return Math.max(leftMaxSum, rightMaxSum);
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
