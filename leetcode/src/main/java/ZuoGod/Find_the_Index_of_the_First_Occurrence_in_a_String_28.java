package ZuoGod;

import java.util.Arrays;


public class Find_the_Index_of_the_First_Occurrence_in_a_String_28 {

    public static void main(String[] args) {
        String s = "abcabcd";
        Find_the_Index_of_the_First_Occurrence_in_a_String_28 res = new Find_the_Index_of_the_First_Occurrence_in_a_String_28();
        int[] next = res.findNext(s.toCharArray());
        System.out.println(Arrays.toString(next));
        String haystack = "hello";
        String needle = "ll";
        int i = res.strStr(haystack, needle);
        System.out.println(i);
    }

    int base = 131;
    long[] hashArray;
    long[] pow;

    /**
     * 字符串哈希
     * @param haystack
     * @param needle
     * @return
     */
    public int strStr(String haystack, String needle) {

        build(haystack);

        long needleHash = hash(needle);
        for (int i = 0, j = needle.length() - 1; j < haystack.length(); i++, j++) {
            if (hash(hashArray,pow, i, j) == needleHash){
                return i;
            }
        }
        return -1;
    }

    private void build(String haystack){
        hashArray = new long[haystack.length()];
        hashArray[0] = haystack.charAt(0) - 'a' + 1;
        for (int i = 1; i < haystack.length(); i++) {
            hashArray[i] = hashArray[i - 1] * base + haystack.charAt(i) - 'a' + 1;
        }

        pow = new long[haystack.length()];
        pow[0] = 1;
        for (int k = 1; k < haystack.length(); k++) {
            pow[k] = pow[k-1] * base;
        }
    }

    private long hash(long[] hashArray,long[] pow, int i, int j) {
        long hash = hashArray[j];
        if (i > 0) {
            hash = hash - hashArray[i - 1] *pow[j - i + 1];
        }
        return hash;
    }

    private long hash(String s) {
        long hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = hash * base + s.charAt(i) - 'a' + 1;
        }
        return hash;
    }

    /**
     * kmp
     *
     * @param haystack
     * @param needle
     * @return
     */
    public int strStr1(String haystack, String needle) {

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
