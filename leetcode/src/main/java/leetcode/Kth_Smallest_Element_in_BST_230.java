package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

public class Kth_Smallest_Element_in_BST_230 {

    private TreeNode res;
    private int i = 1;

    public int kthSmallest(TreeNode root, int k) {
//        inorder(root, k);
//        return res.val;

        //迭代解决
        Deque<TreeNode> stack = new ArrayDeque<TreeNode>();
        TreeNode node = root;
        int i = 1;
        while (!stack.isEmpty() || node != null){
            while (node != null){
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();
            if (i == k){
                break;
            }
            i++;
            node = node.right;
        }
        return node.val;
    }



    /**
     * 递归解法
     * @param root
     * @param k
     */
    private void inorder(TreeNode root, int k) {
        if (root == null){
            return;
        }
        inorder(root.left, k);

        if (k == i++) {
            res = root;
            return;
        }
        inorder(root.right, k);
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
