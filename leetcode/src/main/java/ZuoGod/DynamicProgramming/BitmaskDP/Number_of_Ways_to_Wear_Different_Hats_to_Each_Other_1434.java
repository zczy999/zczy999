package ZuoGod.DynamicProgramming.BitmaskDP;

import java.util.Arrays;
import java.util.List;

public class Number_of_Ways_to_Wear_Different_Hats_to_Each_Other_1434 {

    int mod = 1000000007;

    public int numberWays(List<List<Integer>> hats) {
        int n = hats.size();
        int[][] hatsToPerson = new int[41][n];
        int maxHatNum = 0;
        for (int i = 0; i < n; i++) {
            List<Integer> hatList = hats.get(i);
            for (int j = 0; j < hatList.size(); j++) {
                int hatNum = hatList.get(j);
                maxHatNum = Math.max(maxHatNum, hatNum);
                hatsToPerson[hatNum][i] = 1;
            }
        }

        int[][] dp = new int[41][1 << n];
        for (int i = 0; i <= maxHatNum; i++) {
            Arrays.fill(dp[i], -1);
        }
        //返回
        return f(hatsToPerson, 1, 0, n, maxHatNum, dp);

    }

    private int f(int[][] personToHats, int curHat, int status, int personSize, int maxHatNum, int[][] dp) {
        if (status == (1 << personSize) - 1) {
            return 1;
        }
        if (curHat > maxHatNum) {
            return 0;
        }
        if (dp[curHat][status] != -1) {
            return dp[curHat][status];
        }
        int res1 = f(personToHats, curHat + 1, status, personSize, maxHatNum, dp);
        int res2 = 0;
        for (int i = 0; i < personSize; i++) {
            if ((status & (1 << i)) == 0 && personToHats[curHat][i] == 1) {
                res2 = (res2 + f(personToHats, curHat + 1, status | (1 << i), personSize, maxHatNum, dp)) % mod;
            }

        }
        int res = (res1 + res2) % mod;
        dp[curHat][status] = res;
        return res;
    }

}
