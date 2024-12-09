package ZuoGod.DynamicProgramming;

import java.io.*;
import java.util.Arrays;

// 完成配对需要的最少字符数量
// 给定一个由'['、']'、'('，')'组成的字符串
// 请问最少插入多少个括号就能使这个字符串的所有括号正确配对
// 例如当前串是 "([[])"，那么插入一个']'即可满足
// 输出最少需要插入多少个字符
// 测试链接 : https://www.nowcoder.com/practice/e391767d80d942d29e6095a935a5b96b
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过
public class MinimumInsertionsToMatch {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        String str = br.readLine();
        out.println(compute(str));
        out.flush();
        out.close();
        br.close();
    }

    private static int compute(String str) {
        char[] charArray = str.toCharArray();
        int[][] dp = new int[charArray.length][charArray.length];
        for (int i = 0; i < charArray.length; i++){
            Arrays.fill(dp[i], -1);
        }
        return f(charArray, 0, charArray.length - 1, dp);
    }

    private static int f(char[] charArray, int l, int r, int[][] dp) {
        if (dp[l][r] != -1){
            return dp[l][r];
        }

        if (l == r) {
            return 1;
        }
        if (l == r - 1) {
            boolean res = checkBracketMatching(charArray, l, r);
            return res ? 0 : 2;
        }

        int res = Integer.MAX_VALUE;
        if (checkBracketMatching(charArray, l, r)) {
            res = Math.min(res, f(charArray, l + 1, r - 1,dp));
        }
        for (int i = l; i < r; i++) {
            res = Math.min(res, f(charArray, l, i,dp) + f(charArray, i + 1, r,dp));
        }

        dp[l][r] = res;
        return res;
    }

    private static boolean checkBracketMatching(char[] charArray, int l, int r) {
        return (charArray[l] == '(' && charArray[r] == ')') ||
                (charArray[l] == '[' && charArray[r] == ']');
    }

}
