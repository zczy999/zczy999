package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Merge_Intervals_56 {

    public int[][] merge(int[][] intervals) {
        int[][] res = new int[intervals.length][2];
        Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]);
        int start = -1;
        for (int[] o : intervals) {
            if (start == -1 || o[0] > res[start][1]) {
                res[++start] = o;
            } else {
                res[start][1] = Math.max(res[start][1], o[1]);
            }
        }

        return Arrays.copyOf(res, start + 1);
    }

    public int[][] merge1(int[][] intervals) {
        if (intervals.length == 1) {
            int[][] ans = new int[1][2];
            ans[0] = intervals[0];
            return ans;
        }
        List<int[]> list = new ArrayList<>();
        Arrays.sort(intervals, (o1, o2) -> {
            if (o1[0] == o2[0]) {
                return o1[1] - o2[1];
            }
            return o1[0] - o2[0];
        });
        int l = 0;
        for (int r = 1; r <= intervals.length; r++) {
            if (r == intervals.length) {
                extracted(intervals, l, r, list);
                break;
            }

            if (intervals[r][0] <= intervals[r - 1][1]) {
                continue;
            }
            extracted(intervals, l, r, list);
            l = r;

        }
        int[][] res = new int[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    private static void extracted(int[][] intervals, int l, int r, List<int[]> list) {
        if (l == r - 1) {
            list.add(intervals[l]);
        } else {
            int[] temp = new int[2];
            temp[0] = intervals[l][0];
            temp[1] = Math.max(intervals[r - 1][1], intervals[l][1]);
            list.add(temp);
        }
    }

}
