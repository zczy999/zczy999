package ZuoGod;

import java.util.Arrays;
import java.util.HashMap;

public class Find_the_Index_of_the_First_Occurrence_in_a_String_28 {

    public static void main(String[] args) {
        String s = "abcabcd";
        Find_the_Index_of_the_First_Occurrence_in_a_String_28 res = new Find_the_Index_of_the_First_Occurrence_in_a_String_28();
        int[] next = res.findNext(s.toCharArray());
        System.out.println(Arrays.toString(next));
    }

    public int strStr(String haystack, String needle) {

        int n = haystack.length(), m = needle.length();
        char[] haystackChars = haystack.toCharArray();
        char[] needleChars = needle.toCharArray();
        int[] next = findNext(needleChars);
        int i = 0, j = 0;
        while (i < n && j < m) {
            if (haystackChars[i] == needleChars[j]) {
                i++;
                j++;
            } else if (j == 0) {
                i++;
            } else {
                j = next[j];
            }
        }

        return j == m ? i - j : -1;
    }

    public int[] findNext(char[] needle) {
        if (needle.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[needle.length];
        next[0] = -1;
        next[1] = 0;
        for (int i = 2, j = 0; i < needle.length; i++) {
            while (j > 0 && needle[i - 1] != needle[j]) {
                j = next[j];
            }
            if (needle[i - 1] == needle[j]) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

}
