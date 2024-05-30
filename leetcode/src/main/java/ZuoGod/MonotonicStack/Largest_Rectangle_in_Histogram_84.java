package ZuoGod.MonotonicStack;

import java.util.ArrayDeque;
import java.util.Deque;

public class Largest_Rectangle_in_Histogram_84 {

    public int largestRectangleArea(int[] heights) {
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
