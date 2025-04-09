package leetcode;

import java.util.*;

public class Binary_Tree_Right_Side_View_199 {

    Set<Integer> set;
    List<Integer> res;
    public List<Integer> rightSideView(TreeNode root) {
        this.set = new HashSet<>();
        this.res = new ArrayList<>();
        dfs(root,0);
        return res;
    }

    public void dfs(TreeNode root,int depth) {
        if (root == null){
            return;
        }
//        if (!set.contains(depth)){
        if (res.size() <= depth){
            res.add(root.val);
            set.add(depth);
        }
        dfs(root.right,depth+1);
        dfs(root.left,depth+1);
    }


    /**
     * bfs
     * @param root
     * @return
     */
    public List<Integer> rightSideView1(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> deque = new ArrayDeque<>();
        if (root == null) {
            return res;
        }
        deque.add(root);
        while (!deque.isEmpty()) {
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = deque.poll();
                if (i == size - 1) {
                    res.add(node.val);
                }
                if (node.left != null) {
                    deque.add(node.left);
                }
                if (node.right != null) {
                    deque.add(node.right);
                }
            }
        }
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
