package ZuoGod.DynamicProgramming.TreeDP;

public class Validate_Binary_Search_Tree_98 {

    public class TreeNodeInfo {
        long maxValue;
        long minValue;
        boolean isBST;

        public TreeNodeInfo(long maxValue, long minValue, boolean isBST) {
            this.maxValue = maxValue;
            this.minValue = minValue;
            this.isBST = isBST;
        }
    }

    public boolean isValidBST(TreeNode root) {
        return f(root).isBST;
    }

    private TreeNodeInfo f(TreeNode root) {
        if (root == null) {
            //这里很重要，一个很巧妙的技巧
            return new TreeNodeInfo(Long.MIN_VALUE, Long.MAX_VALUE, true);
        }
        TreeNodeInfo leftNodeInfo = f(root.left);
        TreeNodeInfo rightNodeInfo = f(root.right);
        if (!leftNodeInfo.isBST || !rightNodeInfo.isBST) {
            return new TreeNodeInfo(0, 0, false);
        }

        if (root.val > leftNodeInfo.maxValue && root.val < rightNodeInfo.minValue) {
            long minValue = Math.min(root.val, leftNodeInfo.minValue);
            long maxValue = Math.max(root.val, rightNodeInfo.maxValue);

            return new TreeNodeInfo(maxValue, minValue, true);
        }
        return new TreeNodeInfo(0, 0, false);
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
