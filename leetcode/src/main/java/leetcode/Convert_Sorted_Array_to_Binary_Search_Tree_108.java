package leetcode;

public class Convert_Sorted_Array_to_Binary_Search_Tree_108 {

    public TreeNode sortedArrayToBST(int[] nums) {

        TreeNode node = f(nums, 0, nums.length - 1);
        return node;
    }

    private TreeNode f(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        int mid = left + ((right - left) >> 1);
        TreeNode root = new TreeNode(nums[mid]);
        root.left = f(nums, left, mid - 1);
        root.right = f(nums, mid + 1, right);
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
