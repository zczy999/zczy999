package ZuoGod.DynamicProgramming.TreeDP;

public class Distribute_Coins_in_Binary_Tree_979 {

    public int distributeCoins(TreeNode root) {
        return f(root).diff;
    }

    private Info f(TreeNode root) {
        if (root == null) {
            return new Info(0, 0, 0);
        }
        Info leftNodeInfo = f(root.left);
        Info rightNodeInfo = f(root.right);

        int nodeSum = leftNodeInfo.nodeSum + rightNodeInfo.nodeSum + 1;
        int coinSum = leftNodeInfo.coinSum + rightNodeInfo.coinSum + root.val;
        int diff = Math.abs(nodeSum - coinSum)+leftNodeInfo.diff+rightNodeInfo.diff;
        return new Info(coinSum, nodeSum, diff);
    }


    public class Info {
        public int coinSum;
        public int nodeSum;
        public int diff;

        Info(int a, int b, int c) {
            coinSum = a;
            nodeSum = b;
            diff = c;
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
