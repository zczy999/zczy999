package ZuoGod.DynamicProgramming;

import java.util.Arrays;

public class Minimum_Insertion_Steps_to_Make_a_String_Palindrome_1312 {

    public int minInsertions(String s) {
        int[][] cache = new int[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            Arrays.fill(cache[i], -1);
        }
        return f1(s, 0, s.length() - 1, cache);
    }

    private int f(String s, int l, int r) {
        if (l == r) {
            return 0;
        }
        if (l == r - 1) {
            if (s.charAt(l) == s.charAt(r)) {
                return 0;
            } else {
                return 1;
            }
        }

        if (s.charAt(l) == s.charAt(r)) {
            return f(s, l + 1, r - 1);
        }

        int res = Math.min(f(s, l + 1, r), f(s, l, r - 1)) + 1;
        return res;
    }

    private int f1(String s, int l, int r, int[][] cache) {
        if (cache[l][r] != -1) {
            return cache[l][r];
        }
        int ans;
        if (l == r) {
            ans = 0;
        } else if (l == r - 1) {
            ans = s.charAt(l) == s.charAt(r) ? 0 : 1;
        } else {
            if (s.charAt(l) == s.charAt(r)) {
                ans = f1(s, l + 1, r - 1,cache);
            } else {
                ans = Math.min(f1(s, l + 1, r,cache), f1(s, l, r - 1,cache)) + 1;
            }
        }

        cache[l][r] = ans;
        return ans;
    }

}
