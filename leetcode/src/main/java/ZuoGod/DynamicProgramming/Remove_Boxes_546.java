package ZuoGod.DynamicProgramming;


public class Remove_Boxes_546 {

    public static void main(String[] args) {
        int[] nums = {1, 3, 2, 2, 2, 3, 4, 3, 1};
        Remove_Boxes_546 r = new Remove_Boxes_546();
        int i = r.removeBoxes(nums);
        System.out.println(i);
    }

    public int removeBoxes(int[] boxes) {
        int n = boxes.length;
        int[][][] dp = new int[n][n][n];
        return f(boxes, 0, n - 1, 0, dp);

    }

    private int f(int[] boxes, int l, int r, int count, int[][][] dp) {
        if (l > r) {
            return 0;
        }
        if (dp[l][r][count] > 0) {
            return dp[l][r][count];
        }

        int curNum = boxes[l];
        int curPos = l;
        while (curPos <= r && boxes[curPos] == curNum) {
            curPos++;
        }
        int newCount = curPos - l + count;
        int res;
        res = newCount * newCount + f(boxes, curPos, r, 0, dp);
        for (int i = curPos + 1; i <= r; i++) {
            if (boxes[i] == curNum && boxes[i - 1] != boxes[i]) {
                res = Math.max(res, f(boxes, curPos, i - 1, 0, dp) + f(boxes, i, r, newCount, dp));
            }
        }
        dp[l][r][count] = res;
        return res;
    }

}
