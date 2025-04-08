package ZuoGod.DynamicProgramming.TreeDP;

public class House_Robber_III_337 {

    public int rob(TreeNode root) {
        TreeNodeInfo treeNodeInfo = robTree(root);
        return Math.max(treeNodeInfo.robValue, treeNodeInfo.noRobValue);
    }

    public TreeNodeInfo robTree(TreeNode root) {
        if (root == null){
            return new TreeNodeInfo(0, 0);
        }
        TreeNodeInfo leftNodeInfo = robTree(root.left);
        TreeNodeInfo rightNodeInfo = robTree(root.right);
        int robValue = root.val + leftNodeInfo.noRobValue + rightNodeInfo.noRobValue;
        robValue = Math.max(robValue, leftNodeInfo.robValue + rightNodeInfo.robValue);
        int noRobValue = leftNodeInfo.robValue + rightNodeInfo.robValue;
        return new TreeNodeInfo(robValue, noRobValue);
    }

    public class TreeNodeInfo {
        int robValue;
        int noRobValue;
        public TreeNodeInfo(int robValue, int noRobValue) {
            this.robValue = robValue;
            this.noRobValue = noRobValue;
        }
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
