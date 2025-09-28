package ZuoGod.MonotonicStack;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;

public class Decode_String_394 {

    @Test
    public void test() {
        String s = "3[a]2[bc]";
        String res = decodeString(s);
        System.out.println(res);
        assert res.equals("aaabcbc");
    }

    int where;

    public String decodeString(String s) {
        return f(s.toCharArray(), 0);
    }

    public String f(char[] s, int i) {
        int num = 0;
        StringBuilder res = new StringBuilder();
        while (i < s.length && s[i] != ']') {
            if (Character.isDigit(s[i])) {
                num = num * 10 + (s[i] - '0');
                i++;
            } else if (s[i] != '[') {
                //字符串
                res.append(s[i]);
                i++;
            } else {
                //遇到左括号
                String temp = f(s, i + 1);
                for (int j = 0; j < num; j++) {
                    res.append(temp);
                }
                num = 0;
                i = where + 1;
            }
        }
        where = i;
        return res.toString();
    }

}
