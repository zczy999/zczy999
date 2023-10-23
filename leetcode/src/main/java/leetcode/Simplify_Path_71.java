package leetcode;

import java.util.Deque;
import java.util.LinkedList;

public class Simplify_Path_71 {
    public static void main(String[] args) {
        Simplify_Path_71 res = new Simplify_Path_71();
        String s = res.simplifyPath("/../");
        System.out.println(s);
    }

    public String simplifyPath(String path) {
        Deque<String> stack = new LinkedList<>();
        String[] split = path.split("/");

        for (String s : split) {
            if (s.equals("..")) {
                stack.pollLast();
                continue;
            }
            if (!s.isEmpty() && !s.equals(".")) {
                stack.addLast(s);
            }

        }
        StringBuilder sb = new StringBuilder();
        if (stack.isEmpty()){
            return "/";
        }
        while (!stack.isEmpty()) {
            sb.append("/");
            sb.append(stack.pollFirst());
        }
        return sb.toString();
    }
}
