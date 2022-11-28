package leetcode;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.locks.Lock;

public class Two_Sum_1 {
    public int[] twoSum(int[] nums, int target) {
        int[][] dnums = new int[nums.length][2];
        for (int i = 0; i < nums.length; i++) {
            dnums[i] = new int[]{nums[i], i};
        }

        Arrays.sort(dnums, (o1, o2) -> o1[0] - o2[0]);

        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            if (dnums[left][0] + dnums[right][0] > target) {
                right--;
            } else if (dnums[left][0] + dnums[right][0] < target) {
                left++;
            } else {
                return new int[]{dnums[left][1], dnums[right][1]};
            }


        }
        return new int[]{-1, -1};
    }

    public static Date covertTime(Date date)  {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp now = new Timestamp(date.getTime());
        String str = df.format(now);
        Date newDate = null;
        try {
            newDate = df.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return newDate;
    }

    public static void main(String[] args) {
//        Date date = new Date();
        String tsStr = "2011-05-09 11:49:45.999";
        Date  date = Timestamp.valueOf(tsStr);
        System.out.println(date);
//        date = covertTime(date);
        long time = date.getTime();
        System.out.println(time);
        time = time/1000*1000;
        System.out.println(time);
        date = new Date(time);
        System.out.println(date);
    }
}
