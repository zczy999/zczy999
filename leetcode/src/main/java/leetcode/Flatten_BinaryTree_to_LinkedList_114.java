package leetcode;

public class Flatten_BinaryTree_to_LinkedList_114 {

    public void flatten(TreeNode root) {
        FBL1(root);
    }

    TreeNode p;

    /**
     * 遍历解法
     * @param root
     */
    private void FBL1(TreeNode root) {
        if (root == null) {
            return;
        }
        if (p != null) {
            p.right = root;
            p.left = null;
        }

        p = root;
        TreeNode right = p.right;
        FBL1(p.left);
        FBL1(right);
    }


    /**
     * 分治解法
     *
     * @param root
     * @return
     */
    private TreeNode FBL(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = root.left;
        TreeNode right = root.right;
        TreeNode resL = FBL(left);
        TreeNode resR = FBL(right);

        if (left != null) {
            root.left = null;
            root.right = left;
            resL.right = right;
            if (right != null) {
                return resR;
            }
            return resL;
        }

        if (right != null) {
            return resR;
        }

        return root;
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
