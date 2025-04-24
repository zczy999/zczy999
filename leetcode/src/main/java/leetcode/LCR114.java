package leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * LCR 114. 火星词典
 * https://leetcode.cn/problems/Jf1JuT/description/
 */
public class LCR114 {

    @Test
    public void test() {
        String[] words = {"wrt", "wrf", "er", "ett", "rftt"};
        Assert.assertEquals("wertf", alienOrder(words));
    }

    @Test
    public void test1() {
        String[] words = {"z", "x"};
        Assert.assertEquals("zx", alienOrder(words));
    }

    @Test
    public void test2() {
        String[] words = {"z", "z"};
        Assert.assertEquals("z", alienOrder(words));
    }

    @Test
    public void test3() {
        String[] words = {"ac", "ab", "zc", "zb"};
        String ans = alienOrder(words);
        Assert.assertTrue(ans.equals("acbz") || ans.equals("aczb"));
    }

    @Test
    public void test4() {
        String[] words = {"abc", "ab"};
        Assert.assertEquals("", alienOrder(words));
    }

    @Test
    public void test5() {
        String[] words = {"z","x","a","zb","zx"};
        Assert.assertEquals("", alienOrder(words));
    }



    Map<Character, List<Character>> graph;
    int[] inDegree;

    public String alienOrder(String[] words) {
        if (words == null || words.length == 0) {
            return "";
        }
        this.graph = new HashMap<>();
        this.inDegree = new int[26];
        Arrays.fill(inDegree, -1);
        for (String w : words) {
            for (int i = 0; i < w.length(); i++) {
                inDegree[w.charAt(i) - 'a'] = 0;
            }
        }

        //构造图
        for (int i = 1; i < words.length; i++) {
            if (!process(words[i - 1], words[i])) {
                return "";
            }
        }

        StringBuilder sb = new StringBuilder();
        Deque<Character> queue = new ArrayDeque<>();
        char cur = 0;
        for (int i = 0; i < 26; i++) {
            if (inDegree[i] == 0) {
                cur = (char) ('a' + i);
                queue.addLast(cur);
            }
        }
        if (cur == 0) {
            return "";
        }

        while (!queue.isEmpty()) {
            Character polled = queue.pollFirst();
            sb.append(polled);
            if (graph.get(polled) == null) {
                continue;
            }
            for (Character next : graph.get(polled)) {
                if (--inDegree[next - 'a'] == 0) {
                    queue.addLast(next);
                }
            }
        }

        // 统计所有出现过的字母数量
        int totalChars = 0;
        for (int i = 0; i < 26; i++) {
            if (inDegree[i] != -1) {
                totalChars++;
            }
        }

        return totalChars == sb.length() ? sb.toString() : "";
    }

    private boolean process(String pre, String cur) {
        char[] preCharArray = pre.toCharArray();
        char[] curCharArray = cur.toCharArray();
        int len = Math.min(preCharArray.length, curCharArray.length);
        for (int i = 0; i < len; i++) {
            if (preCharArray[i] != curCharArray[i]) {
                if (!graph.containsKey(preCharArray[i])) {
                    graph.put(preCharArray[i], new ArrayList<>());
                }
                graph.get(preCharArray[i]).add(curCharArray[i]);
                inDegree[curCharArray[i] - 'a']++;
                return true; // 有不同字符，合法
            }
        }
        // 如果前缀完全相同，且pre更长，则无效
        if (preCharArray.length > curCharArray.length) {
            return false;
        }
        return true;
    }


}
