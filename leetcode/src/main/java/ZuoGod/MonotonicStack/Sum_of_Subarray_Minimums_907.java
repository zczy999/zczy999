package ZuoGod.MonotonicStack;

import java.util.Deque;
import java.util.LinkedList;

public class Sum_of_Subarray_Minimums_907 {

    public int sumSubarrayMins(int[] arr) {
        Deque<Integer> stack = new LinkedList<>();
        int MOD = 1000000007;
        long ans = 0;
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                int cur = stack.pop();
                int left = stack.isEmpty() ? -1 : stack.peek();
                ans = (ans + (long) (cur - left) * (i - cur) * arr[cur]) % MOD;
            }
            stack.push(i);
        }
        int len = arr.length;
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            int left = stack.isEmpty() ? -1 : stack.peek();
            ans = (ans + (long) (cur - left) * (len - cur) * arr[cur]) % MOD;
        }
        return (int) ans;
    }

}
