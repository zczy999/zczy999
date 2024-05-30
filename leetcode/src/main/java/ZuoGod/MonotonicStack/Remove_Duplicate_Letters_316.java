package ZuoGod.MonotonicStack;

import java.util.ArrayDeque;
import java.util.Deque;

public class Remove_Duplicate_Letters_316 {

    public static void main(String[] args) {
        Remove_Duplicate_Letters_316 test = new Remove_Duplicate_Letters_316();
        System.out.println(test.removeDuplicateLetters("bcabc"));
    }

    public String removeDuplicateLetters(String s) {
        int[] count = new int[26];
        int[] flag = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (flag[c - 'a'] > 0) {
                count[c - 'a']--;
                continue;
            }
            while (!stack.isEmpty() && c < stack.peek()) {
                if (count[stack.peek() - 'a'] == 0) {
                   break;
                }
                Character pop = stack.pop();
                flag[pop - 'a']--;
            }

            stack.push(c);
            flag[c - 'a']++;
            count[c - 'a']--;
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString();
    }

}
