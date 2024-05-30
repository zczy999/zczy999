package ZuoGod.DynamicProgramming;

import java.util.Arrays;

public class Minimum_Cost_For_Tickets_983 {
    public static void main(String[] args) {
        Minimum_Cost_For_Tickets_983 res = new Minimum_Cost_For_Tickets_983();
        int[] days = {1, 4, 6, 7, 8, 20};
        int[] costs = {2, 7, 15};
        int i = res.mincostTickets(days, costs);
        System.out.println(i);
    }


    int[] res;

    int[] dirs = {1, 7, 30};

    public int mincostTickets(int[] days, int[] costs) {
        res = new int[days.length];
        Arrays.fill(res, Integer.MAX_VALUE);
        return f(days, costs, 0);
    }

    private int f(int[] days, int[] costs, int i) {
        if (i > days.length - 1) {
            return 0;
        }
        if (res[i] != Integer.MAX_VALUE){
            return res[i];
        }
        for (int j = 0; j < dirs.length; j++) {
            int dir = dirs[j];
            int day = days[i];
            day += dir;
            int flag = 0;
            while (i + flag < days.length && days[i + flag] < day) {
                flag++;
            }
            res[i] = Math.min(res[i], costs[j] + f(days, costs, i + flag));
        }
        return res[i];
    }

}
