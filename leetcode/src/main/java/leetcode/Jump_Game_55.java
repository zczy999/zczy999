package leetcode;

import java.util.ArrayDeque;
import java.util.Queue;

public class Jump_Game_55 {
    /**
     * bfs
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        if (nums.length == 1) return true;
        boolean[] flag = new boolean[nums.length];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(0);

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            int next = cur + nums[cur];
            for (int i = cur + 1; i <= next && i < nums.length; i++) {
                if (flag[i] == true) continue;
                queue.offer(i);
                flag[i] = true;
                if (i == nums.length - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 贪心
     */
    public boolean canJump1(int[] nums) {
        int n = nums.length;
        int rightmost = 0;
        for (int i = 0; i < n; ++i) {
            if (i <= rightmost) {
                rightmost = Math.max(rightmost, i + nums[i]);
                if (rightmost >= n - 1) {
                    return true;
                }
            }
        }
        return false;
    }


}
