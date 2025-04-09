package leetcode;

public class Count_Complete_Tree_Nodes_222 {


    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = findDepth(root.left);
        int rightDepth = findDepth(root.right);
        if (leftDepth == rightDepth) {
            if (leftDepth == 0) {
                return 1;
            }
            //这里需要注意 左子树是满二叉树，右子树是普通二叉树
            int rightNodeNum = countNodes(root.right);
            //满二叉树的节点数是 2^leftDepth - 1
            return (1 << leftDepth) + rightNodeNum;
        }else {
            int leftNodeNum = countNodes(root.left);
            return leftNodeNum + (1 << rightDepth);
        }

    }

    private int findDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return findDepth(root.left) + 1;
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
