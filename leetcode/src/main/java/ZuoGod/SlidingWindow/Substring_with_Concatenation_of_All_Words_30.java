package ZuoGod.SlidingWindow;


import java.util.*;

public class Substring_with_Concatenation_of_All_Words_30 {

    public static void main(String[] args) {
        Substring_with_Concatenation_of_All_Words_30 res = new Substring_with_Concatenation_of_All_Words_30();;
        String[] words = {"a","a"};
        String s = "aaa";
        List<Integer> integers = res.findSubstring(s, words);
        System.out.println(integers.toString());
    }

    int base = 131;
    long[] hashArray;
    long[] pow;

    /**
     * 字符串哈希+滑动窗口
     * @param s
     * @param words
     * @return
     */
    public List<Integer> findSubstring(String s, String[] words) {
        build(s);
        HashMap<Long, Integer> wordsMap = new HashMap<>();
        for (String word : words) {
            wordsMap.put(hash(word), wordsMap.getOrDefault(hash(word), 0) + 1);
        }
        int wordArrayLen = words.length;
        int wordLen = words[0].length();
        int allLen = wordArrayLen * wordLen;
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < wordLen && i+ allLen <= s.length(); i++) {
            HashMap<Long, Integer> window = new HashMap<>();
            int debt = wordArrayLen;
            int l = i, r = i;
            // 滑动窗口初始化 先加入wordArrayLen个单词
            for (int j = 0; j < wordArrayLen; j++) {
                long cur = hash(r, r + wordLen - 1);
                window.put(cur, window.getOrDefault(cur, 0) + 1);
                if (window.get(cur) <= wordsMap.getOrDefault(cur,0)) {
                    debt--;
                }
                r = r + wordLen;
            }
            if (debt == 0) {
                res.add(l);
            }

            while (r + wordLen - 1 < s.length()) {
                long pre = hash(l, l + wordLen - 1);
                window.put(pre, window.get(pre) - 1);
                if (window.get(pre) < wordsMap.getOrDefault(pre,0)) {
                    debt++;
                }

                long cur = hash(r, r + wordLen - 1);
                window.put(cur, window.getOrDefault(cur, 0) + 1);
                if (window.get(cur) <= wordsMap.getOrDefault(cur,0)) {
                    debt--;
                }

                if (debt == 0) {
                    res.add(l + wordLen);
                }

                l = l + wordLen;
                r = r + wordLen;
            }

        }

        return res;
    }

    private void build(String haystack) {
        hashArray = new long[haystack.length()];
        hashArray[0] = haystack.charAt(0) - 'a' + 1;
        for (int i = 1; i < haystack.length(); i++) {
            hashArray[i] = hashArray[i - 1] * base + haystack.charAt(i) - 'a' + 1;
        }

        pow = new long[haystack.length()];
        pow[0] = 1;
        for (int k = 1; k < haystack.length(); k++) {
            pow[k] = pow[k - 1] * base;
        }
    }

    private long hash(int i, int j) {
        long hash = hashArray[j];
        if (i > 0) {
            hash = hash - hashArray[i - 1] * pow[j - i + 1];
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

    public List<Integer> findSubstring1(String s, String[] words) {
        Map<String, Integer> sets = new HashMap<>();
        List<Integer> res = new ArrayList<>();
        for (String word : words) {
            sets.put(word, sets.getOrDefault(word, 0) + 1);
        }

        int n = words[0].length();
        int sum = n * words.length;

        for (int i = 0; i < s.length() - sum + 1; i++) {
            //substring重点
            String tmp = s.substring(i, i + sum);
            Map<String, Integer> set = new HashMap<>();
            for (int j = 0; j < sum; j = j + n) {
                String w = tmp.substring(j, j + n);
                set.put(w, set.getOrDefault(w, 0) + 1);
            }
            if (set.equals(sets)) {
                res.add(i);
            }
        }
        return res;
    }

}
