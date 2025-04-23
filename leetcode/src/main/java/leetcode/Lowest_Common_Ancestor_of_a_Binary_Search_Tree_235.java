package leetcode;

public class Lowest_Common_Ancestor_of_a_Binary_Search_Tree_235 {

    /**
     * 只要root在p和q之间，说明p和q被root“分开”，root就是最近公共祖先。
     * 如果root比p和q都大，说明p和q都在左子树，向左走。
     * 如果root比p和q都小，说明p和q都在右子树，向右走。
     *
     * 直到遇到分叉点（即root在中间），此时就是最近公共祖先
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root.val>p.val && root.val>q.val) return lowestCommonAncestor(root.left, p, q);
        if(root.val<p.val && root.val<q.val) return lowestCommonAncestor(root.right,p,q);
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
