package leetcode;

import java.util.ArrayList;
import java.util.List;

public class Insert_Interval_57 {

    public int[][] insert(int[][] intervals, int[] newInterval) {

        int l = 0, r = 0;
        List<int[]> res = new ArrayList<>();
        if (intervals.length == 0){
            res.add(newInterval);
            return res.toArray(new int[res.size()][]);
        }
        while (l < intervals.length && intervals[l][1] < newInterval[0]){
            res.add(intervals[l]);
            l++;
        }
        r = l;
        while (r < intervals.length && intervals[r][0] <= newInterval[1]){
            //进一个更新一次 不会漏 很重要
            newInterval[0] = Math.min(intervals[r][0], newInterval[0]);
            newInterval[1] = Math.max(intervals[r][1], newInterval[1]);
            r++;
        }
        res.add(newInterval);
        while (r < intervals.length){
            res.add(intervals[r]);
            r++;
        }
        return res.toArray(new int[res.size()][]);
    }

}
