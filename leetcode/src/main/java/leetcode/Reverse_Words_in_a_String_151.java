package leetcode;

public class Reverse_Words_in_a_String_151 {

    class Solution {
        public String reverseWords(String s) {
            s = s.trim();                                    // 删除首尾空格
            int j = s.length() - 1, i = j;
            StringBuilder res = new StringBuilder();
            while (i >= 0) {
                while (i >= 0 && s.charAt(i) != ' ') i--;     // 搜索首个空格
                res.append(s.substring(i + 1, j + 1) + " "); // 添加单词
                while (i >= 0 && s.charAt(i) == ' ') i--;     // 跳过单词间空格
                j = i;                                       // j 指向下个单词的尾字符
            }
            return res.toString().trim();                    // 转化为字符串并返回
        }
    }

    /**
     * 问题
     * 1.忽略了while的使用
     * 2.对于功能重复的代码应该用方法封装
     * @param s
     * @return
     */
    public static String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        int start = 0, end = 0;
        boolean flag = false;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) != ' ' && !flag) {
                start = i;
                end = i;
                if (end == 0) {
                    if (sb.length() != 0) {
                        sb.append(' ');
                    }
                    sb.append(s.charAt(end));
                }
                flag = true;
                continue;
            }
            if (s.charAt(i) != ' ' && flag) {
                start = i;
                if (start == 0) {
                    if (sb.length() != 0) {
                        sb.append(' ');
                    }
                    for (int j = start; j <= end; j++) {
                        sb.append(s.charAt(j));
                    }
                }
                continue;
            }
            if (flag) {
                if (sb.length() != 0) {
                    sb.append(' ');
                }
                for (int j = start; j <= end; j++) {
                    sb.append(s.charAt(j));
                }
            }

            flag = false;
        }
        return sb.toString();
    }

    public String reverseWords1(String s) {
        StringBuilder sb = new StringBuilder();
        int start = 0, end = 0;
        boolean flag = false;
        for (int i = s.length() - 1; i >= 0; i--) {
            char currentChar = s.charAt(i);
            if (currentChar != ' ' && !flag) {
                start = i;
                end = i;
                if (end == 0) {
                    appendWord(sb, s, start, end);
                }
                flag = true;
                continue;
            }
            if (currentChar != ' ' && flag) {
                start = i;
                if (start == 0) {
                    appendWord(sb, s, start, end);
                }
                continue;
            }
            if (flag) {
                appendWord(sb, s, start, end);
            }
            flag = false;
        }
        return sb.toString();
    }

    private void appendWord(StringBuilder sb, String s, int start, int end) {
        if (sb.length() != 0) {
            sb.append(' ');
        }
        for (int j = start; j <= end; j++) {
            sb.append(s.charAt(j));
        }
    }

    public static void main(String[] args) {
        String s = "a";
        String s1 = Reverse_Words_in_a_String_151.reverseWords(s);
        System.out.println(s1);

    }
}
