package leetcode;

import java.util.Deque;
import java.util.LinkedList;


public class Binary_Search_Tree_Iterator_173 {

    private Deque<TreeNode> stack;
    private TreeNode cur;

    public Binary_Search_Tree_Iterator_173(TreeNode root) {
        stack = new LinkedList<>();
        cur = root;
    }

    public int next() {
        while (cur != null){
            stack.push(cur);
            cur = cur.left;
        }
        TreeNode popNode = stack.pop();
        cur = popNode.right;
        return popNode.val;
    }

    public boolean hasNext() {
        return stack.size() > 0 || cur != null ;
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
