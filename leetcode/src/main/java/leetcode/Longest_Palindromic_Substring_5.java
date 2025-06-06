package leetcode;

public class Longest_Palindromic_Substring_5 {

    public String longestPalindrome(String s) {

        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expand(s, i, i);
            int len2 = expand(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                //这段代码的功能是根据当前找到的回文子串长度 len，更新最长回文子串的起始和结束位置：
                //start = i - (len - 1) / 2;：计算当前回文的起始索引。
                //end = i + len / 2;：计算当前回文的结束索引。
                //这两个操作确保了无论回文长度是奇数还是偶数，都能正确更新对应的位置。
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private int expand(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;

    }

}
