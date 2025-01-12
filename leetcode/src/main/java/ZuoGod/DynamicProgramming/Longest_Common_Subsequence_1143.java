package ZuoGod.DynamicProgramming;

import java.util.Arrays;

public class Longest_Common_Subsequence_1143 {

    public static void main(String[] args) {
        Longest_Common_Subsequence_1143 res = new Longest_Common_Subsequence_1143();
        String text1 = "abcded";
        String text2 = "aced";
        int i = res.longestCommonSubsequence(text1, text2);
        String s = res.longestCommonSubsequence1(text1, text2);
        System.out.println(i);
        System.out.println(s);
    }

    private int[][] dp;

    /**
     * 递归求解最长公共子序列长度
     *
     * @param text1
     * @param text2
     * @return
     */
    public int longestCommonSubsequence(String text1, String text2) {
        char[] text1CharArray = text1.toCharArray();
        char[] text2CharArray = text2.toCharArray();
        dp = new int[text1CharArray.length + 1][text2CharArray.length + 1];
        for (int i = 0; i < text1CharArray.length; i++) {
            Arrays.fill(dp[i], -1);
        }
        return f(0, text1CharArray, 0, text2CharArray);

    }

    /**
     * 输出最长公共子序列
     *
     * @param text1
     * @param text2
     * @return
     */
    public String longestCommonSubsequence1(String text1, String text2) {
        char[] text1CharArray = text1.toCharArray();
        char[] text2CharArray = text2.toCharArray();
        dp = new int[text1CharArray.length + 1][text2CharArray.length + 1];
        for (int i = 0; i < text1CharArray.length; i++) {
            Arrays.fill(dp[i], -1);
        }
        int len = f(0, text1CharArray, 0, text2CharArray);
        int i = 0, j = 0;
        StringBuilder sb = new StringBuilder();
        while (len>0){
            if (text1CharArray[i] == text2CharArray[j]){
                sb.append(text1CharArray[i]);
                i++;
                j++;
                len--;
            }else if (dp[i+1][j]>dp[i][j+1]){
                i++;
            }else {
                j++;
            }
        }
        return sb.toString();
    }

    private int f(int i, char[] text1CharArray, int j, char[] text2CharArray) {
        if (i == text1CharArray.length || j == text2CharArray.length) {
            return 0;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        int res = 0;
        if (text1CharArray[i] == text2CharArray[j]) {
            res = f(i + 1, text1CharArray, j + 1, text2CharArray) + 1;
        }
        res = Math.max(res, f(i + 1, text1CharArray, j, text2CharArray));
        res = Math.max(res, f(i, text1CharArray, j + 1, text2CharArray));
        dp[i][j] = res;
        return res;
    }
}
