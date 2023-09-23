package leetcode;

import java.util.ArrayList;
import java.util.List;

public class Text_Justification_68 {
    public static void main(String[] args) {
        String[] words = {"This", "is", "an", "example", "of", "text", "justification."};
//        String[] words = {"ask", "not", "what", "your", "country", "can", "do", "for", "you", "ask", "what", "you", "can", "do", "for", "your", "country"};
//        String[] words = {"The", "important", "thing", "is", "not", "to", "stop", "questioning.", "Curiosity", "has", "its", "own", "reason", "for", "existing."};
        List<String> strings = Text_Justification_68.fullJustify(words, 16);
        for (String s : strings) {
            System.out.println(s);
        }
    }

    public static List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        int i = 0;
        while (i < words.length) {
            int res = 0;
            int num = 0;
            int start = 0;
            int end = 0;
            //算出一行的个数
            while (i < words.length && res <= maxWidth) {
                if (res == 0) {
                    start = i;
                }
                if (num != 0) {
                    res++;
                }
                res += words[i].length();
                end = i;
                i++;
                num++;
            }
            //分配空格
            if (res > maxWidth) {
                num--;
                i--;
                end--;
                int len = 0;
                for (int j = start; j <= end; j++) {
                    len += words[j].length();
                }
                int spaceLen;
                StringBuilder sb = new StringBuilder();
                //num == 1时的情况
                if (num == 1) {
                    sb.append(words[start]);
                    int temp = maxWidth - sb.length();
                    while (temp > 0) {
                        sb.append(' ');
                        temp--;
                    }
                    result.add(sb.toString());
                    continue;
                }
                //可以平均分配的情况
                if ((maxWidth - len) % (num - 1) == 0) {
                    spaceLen = (maxWidth - len) / (num - 1);
                    for (int j = start; j <= end; j++) {
                        sb.append(words[j]);
                        for (int l = 0; l < spaceLen; l++) {
                            sb.append(' ');
                        }
                    }
                } else {
                    //不能被平均分配的情况
                    int spareSpaceLen = (maxWidth - len) % (num - 1);
                    spaceLen = (maxWidth - len) / (num - 1);
                    for (int j = start; j <= end; j++) {
                        sb.append(words[j]);
                        if (spareSpaceLen > 0) {
                            sb.append(' ');
                            spareSpaceLen--;
                        }
                        for (int l = 0; l < spaceLen; l++) {
                            sb.append(' ');
                        }
                    }
                }
                result.add(sb.toString().trim());
            } else {
                StringBuilder sb = new StringBuilder();
                //最后一行的情况
                for (int j = start; j <= end; j++) {
                    sb.append(words[j]);
                    sb.append(' ');
                }
                String s = sb.toString().trim();
                sb = new StringBuilder(s);
                int len = maxWidth - s.length();
                while (len > 0) {
                    sb.append(' ');
                    len--;
                }
                result.add(sb.toString());
            }
        }
        return result;
    }
}
