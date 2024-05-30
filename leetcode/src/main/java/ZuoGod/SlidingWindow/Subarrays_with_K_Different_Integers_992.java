package ZuoGod.SlidingWindow;

import java.util.HashMap;
import java.util.Map;

public class Subarrays_with_K_Different_Integers_992 {

    public int subarraysWithKDistinct(int[] nums, int k) {
        return help(nums, k) - help(nums, k - 1);
    }

    private int help(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int times = 0;
        int res = 0;

        for (int left = 0, right = 0; right <= nums.length; right++) {
            while (times > k) {
                int num = nums[left];
                map.put(num, map.get(num) - 1);
                left++;
                if (map.get(num) == 0) {
                    map.remove(num);
                    times--;
                }
            }
            res += right - left + 1;
            if (right == nums.length) {
                break;
            }

            int rnum = nums[right];
            if (!map.containsKey(rnum)) {
                map.put(rnum, 0);
                times++;
            }
            map.put(rnum, map.get(rnum) + 1);
        }

        return res;
    }


}
