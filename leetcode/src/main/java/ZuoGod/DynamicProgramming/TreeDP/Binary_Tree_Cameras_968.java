package ZuoGod.DynamicProgramming.TreeDP;

public class Binary_Tree_Cameras_968 {

    private int ans;
    public int minCameraCover(TreeNode root) {
        ans = 0;
        int res = f(root);
        if (res == 1){
            ans++;
        }
        return ans;
    }

    /**
     * 1 无相机 无覆盖
     * 2 无相机 有覆盖
     * 3 有相机
     * @param root
     * @return
     */
    private int f(TreeNode root){
        if (root == null){
            return 2;
        }

        int left = f(root.left);
        int right = f(root.right);
        if (left == 1 || right == 1){
            ans++;
            return 3;
        }
        if (left == 3 || right == 3){
            return 2;
        }
        return 1;
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
