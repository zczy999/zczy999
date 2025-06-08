package leetcode;

import java.util.*;

public class Word_Break_139 {

    private Set<String> wordSet;
    private List<String> wordList;
    private int maxWordLen;

    /**
     * 基于字典单词长度优化的 DP
     * 内层不枚举所有 j，而是枚举字典中每个单词 w：
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        // 初始化字典集合和单词列表
        wordSet = new HashSet<>(wordDict);
        wordList = new ArrayList<>(wordSet);
        maxWordLen = 0;
        for (String w : wordList) {
            maxWordLen = Math.max(maxWordLen, w.length());
        }

        int n = s.length();
        // dp[i] 表示前 i 个字符（[0, i)）能否被拆分
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;  // 空串可拆分

        // 自底向上填表
        for (int i = 1; i <= n; i++) {
            // 只枚举可能的单词长度，而不枚举所有 j
            for (String w : wordList) {
                int len = w.length();
                if (len > i || len > maxWordLen) continue;
                // 如果前 i−len 可拆分，且 s[i-len .. i) 恰好等于 w
                if (dp[i - len] && s.regionMatches(i - len, w, 0, len)) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[n];
    }




    int[] dp;
    public boolean wordBreak1(String s, List<String> wordDict) {
        wordSet = new HashSet<>(wordDict);
        dp = new int[s.length()];
        Arrays.fill(dp, -1);
        return f(s, 0);
    }

    public boolean f(String s, int i) {
        if (s.length() == i) {
            return true;
        }
        if (dp[i] != -1) {
            return dp[i] == 1;
        }
        for (int j = i; j <= s.length(); j++) {
            String sub = s.substring(i, j);
            if (wordSet.contains(sub)){
                if (f(s, j)) {
                    dp[i] = 1;
                    return true;
                }
            }
        }
        dp[i] = 0;
        return false;
    }

}
