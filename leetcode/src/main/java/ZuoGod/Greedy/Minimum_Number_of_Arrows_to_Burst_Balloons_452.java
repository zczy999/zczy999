package ZuoGod.Greedy;

import java.util.Arrays;
import java.util.Comparator;

public class Minimum_Number_of_Arrows_to_Burst_Balloons_452 {

    public int findMinArrowShots(int[][] points) {
        Arrays.sort(points, Comparator.comparingInt(a -> a[1]));
        long curRight = Long.MIN_VALUE; // 用 long 避免比较时溢出
        int res = 0;
        for (int[] point : points) {
            if (point[0] > curRight) {
                res++;
                curRight = point[1];
            }
        }
        return res;
    }

}
