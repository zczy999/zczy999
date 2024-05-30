package ZuoGod.DynamicProgramming;

import java.util.Arrays;

public class Decode_Ways_91 {

    public static void main(String[] args) {
        Decode_Ways_91 res = new Decode_Ways_91();
        String s = "226";
        int i = res.numDecodings(s);
        System.out.println(i);
    }

    int[] mem;

    public int numDecodings(String s) {
        mem = new int[s.length()];
        Arrays.fill(mem,-1);
        char[] sCharArray = s.toCharArray();
        if (sCharArray[0] == '0') {
            return 0;
        }
        return f(sCharArray, 0);
    }

    /**
     * s[i...] i到最后有多少种方案
     * @param sCharArray
     * @param i
     * @return
     */
    private int f(char[] sCharArray, int i) {
        int len = sCharArray.length;
        if (i == len) {
            return 1;
        }
        if (mem[i] != -1){
            return mem[i];
        }

        int num = 0;

        int n1 = sCharArray[i] - '0';
        if (n1 == 0) {
            num = 0;
        } else {
            num += f(sCharArray, i + 1);

            if (i + 1 < len) {
                int n2 = sCharArray[i + 1] - '0';
                int sum = n1 * 10 + n2;
                if (sum <= 26) {
                    num += f(sCharArray, i + 2);
                }
            }
        }
        mem[i] = num;
        return num;
    }

}
