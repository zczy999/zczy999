package zuoshen.MonotonicStack;

import java.util.ArrayDeque;
import java.util.Deque;

public class Maximal_Rectangle_85 {

    public int maximalRectangle(char[][] matrix) {
        int[] heights = new int[matrix[0].length];
        int res = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == '0') {
                    heights[j] = 0;
                } else {
                    heights[j]++;
                }
            }
            res = Math.max(res, f(heights));
        }
        return res;
    }


    /**
     * 复用84题代码
     *
     * @param heights
     * @return
     */
    private int f(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        int res = 0;
        for (int i = 0; i < heights.length; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                int cur = stack.pop();
                int left = stack.isEmpty() ? -1 : stack.peek();
                res = Math.max(res, heights[cur] * (i - left - 1));
            }
            stack.push(i);
        }
        int len = heights.length;
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            int left = stack.isEmpty() ? -1 : stack.peek();
            res = Math.max(res, heights[cur] * (len - left - 1));
        }
        return res;
    }
}
