package ZuoGod.DynamicProgramming.BitmaskDP;

public class Can_I_Win_464 {

    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal == 0) {
            return true;
        }
        if (desiredTotal > maxChoosableInteger * (maxChoosableInteger + 1) / 2) {
            return false;
        }
        // dp[state] = 1表示当前先手赢，-1表示当前先手输
        int[] dp = new int[1 << (maxChoosableInteger + 1)];
        return f(maxChoosableInteger, desiredTotal, (1 << maxChoosableInteger + 1) - 1, 0, dp);

    }

    // 返回当前的先手能不能赢，能赢返回true，不能赢返回false
    private boolean f(int maxChoosableInteger, int desiredTotal, int state, int currSum, int[] dp) {
        //当过来到的数字和已经使用的数字的和大于desiredTotal，说明对手已经赢了，返回false
        if (currSum >= desiredTotal) {
            return false;
        }
        if (dp[state] != 0) {
            return dp[state] == 1;
        }

        boolean ans = false;
        for (int i = 1; i <= maxChoosableInteger; i++) {
            //如果i没用 且使用后对手没赢
            if ((state & (1 << i)) != 0 && !f(maxChoosableInteger, desiredTotal, state ^ (1 << i), currSum + i, dp)) {
                ans = true;
                break;
            }
        }
        dp[state] = ans ? 1 : -1;
        return ans;
    }
}
