package leetcode;

public class Longest_Common_Prefix_14 {
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 1){
            return strs[0];
        }

        String res = findLongestCommonPrefix(strs, 0, strs.length - 1);
        return res;
    }

    private String findLongestCommonPrefix(String[] strs, int start, int end) {
        if (start == end) {
            return strs[start];
        }
        int mid = (start + end) / 2;
        String left = findLongestCommonPrefix(strs, start, mid);
        String right = findLongestCommonPrefix(strs, mid + 1, end);
        return computeLongestCommonPrefix(left, right);

    }

    private String computeLongestCommonPrefix(String left, String right) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(left.length(), right.length()); i++) {
            if(left.charAt(i) == right.charAt(i)){
                sb.append(left.charAt(i));
                continue;
            }
            break;
        }
        return sb.toString();
    }
}
