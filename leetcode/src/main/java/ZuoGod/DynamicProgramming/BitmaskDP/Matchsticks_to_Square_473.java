package ZuoGod.DynamicProgramming.BitmaskDP;

public class Matchsticks_to_Square_473 {

    public boolean makesquare(int[] matchsticks) {
        int sum = 0;
        for (int matchstick : matchsticks) {
            sum += matchstick;
        }
        if (sum % 4 != 0) {
            return false;
        }
        int[] dp = new int[1 << matchsticks.length];
        return f(0, 0, 4, sum / 4, matchsticks, dp);

    }

    private boolean f(int status, int curlen, int rest, int limit, int[] matchsticks, int[] dp) {

        if (status == (1 << matchsticks.length) - 1 && rest == 0) {
            return true;
        }
        if (dp[status] != 0) {
            return dp[status] == 1;
        }
        boolean ans = false;
        for (int i = 0; i < matchsticks.length; i++) {
            if ((status & (1 << i)) == 0 && curlen + matchsticks[i] <= limit) {
                if (curlen + matchsticks[i] == limit) {
                    ans = f(status | (1 << i), 0, rest - 1, limit, matchsticks, dp);
                } else {
                    ans = f(status | (1 << i), curlen + matchsticks[i], rest, limit, matchsticks, dp);
                }
                if (ans) {
                    break;
                }
            }
        }
        dp[status] = ans ? 1 : -1;
        return ans;
    }
}
