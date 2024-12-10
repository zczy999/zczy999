package ZuoGod.DynamicProgramming;

public class Strange_Printer_664 {

    public int strangePrinter(String s) {
        char[] charArray = s.toCharArray();
        int[][] dp = new int[s.length()][s.length()];
        return f(charArray, 0, charArray.length - 1,dp);
    }

    private int f(char[] charArray, int l, int r,int[][] dp) {
        if (dp[l][r] != 0){
            return dp[l][r];
        }
        if (l == r) {
            return 1;
        }
        if (l == r - 1) {
            return charArray[l] == charArray[r] ? 1 : 2;
        }

        int res = Integer.MAX_VALUE;
        if (charArray[l] == charArray[r]) {
            res = Math.min(res, f(charArray, l + 1, r,dp));
        }

        for (int i = l; i < r; i++) {
            res = Math.min(res, f(charArray, l, i,dp) + f(charArray, i + 1, r,dp));
        }
        dp[l][r] = res;
        return res;
    }

}
