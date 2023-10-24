package leetcode;

import java.util.Deque;
import java.util.LinkedList;

public class Evaluate_Reverse_Polish_Notation_150 {
    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new LinkedList<>();
        for (String item : tokens) {
            if ("+".equals(item) || "-".equals(item) || "*".equals(item) || "/".equals(item)) {
                int p1 = stack.pop();
                int p2 = stack.pop();
                if ("+".equals(item)) stack.push(p2 + p1);
                else if ("-".equals(item)) stack.push(p2 - p1);
                else if ("*".equals(item)) stack.push(p2 * p1);
                else if ("/".equals(item)) stack.push(p2 / p1);
            }else {
                stack.push(Integer.valueOf(item));
            }

        }
        return stack.pop();
    }
}
