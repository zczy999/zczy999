package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Kth_Smallest_Element_in_BST_230 {

    /**
     * 记录子树的结点数，然后二分
     * @param root
     * @param k
     * @return
     */
    public int kthSmallest1(TreeNode root, int k) {
        Map<TreeNode, Integer> map = new HashMap<>();
        countTreeNum(root, map);
        while (k > 0){
            int left = map.getOrDefault(root.left,0);
            if (k<= left){
                root = root.left;
                continue;
            }
            if (k - left == 1){
                return root.val;
            }
            k = k - left - 1;
            root = root.right;

        }
        return root.val;

    }

    private int countTreeNum(TreeNode root, Map<TreeNode, Integer> map) {
        if (root == null) {
            return 0;
        }
        int leftNum = countTreeNum(root.left, map);
        int rightNum = countTreeNum(root.right, map);
        int sum = leftNum + rightNum + 1;
        map.put(root, sum);
        return sum;
    }

    private TreeNode res;
    private int i = 1;

    /**
     * 中序遍历
     * @param root
     * @param k
     * @return
     */
    public int kthSmallest(TreeNode root, int k) {
//        inorder(root, k);
//        return res.val;

        //迭代解决
        Deque<TreeNode> stack = new ArrayDeque<TreeNode>();
        TreeNode node = root;
        int i = 1;
        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();
            if (i == k) {
                break;
            }
            i++;
            node = node.right;
        }
        return node.val;
    }


    /**
     * 递归解法
     *
     * @param root
     * @param k
     */
    private void inorder(TreeNode root, int k) {
        if (root == null) {
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
