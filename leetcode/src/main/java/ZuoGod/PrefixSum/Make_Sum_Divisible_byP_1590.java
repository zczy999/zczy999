package ZuoGod.PrefixSum;

import java.util.HashMap;
import java.util.Map;

public class Make_Sum_Divisible_byP_1590 {

    public static void main(String[] args) {
        Make_Sum_Divisible_byP_1590 res = new Make_Sum_Divisible_byP_1590();
//        int[] nums = {8,32,31,18,34,20,21,13,1,27,23,22,11,15,30,4,2};
        int[] nums = {1, 2, 3};

        int p = 7;
        int i = res.minSubarray(nums, p);
        System.out.println(i);
    }

    /**
     * 使用前缀和要特别注意int值是否会越界的问题
     *
     * @param nums
     * @param p
     * @return
     */
    public int minSubarray(int[] nums, int p) {
        // 整体余数
        int n = 0;
        for (int num : nums) {
            n = (n + num) % p;
        }
        if (n == 0) {
            return 0;
        }

        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int min = Integer.MAX_VALUE;
        int n1 = 0;
        for (int i = 0; i < nums.length; i++) {
            n1 = (n1 + nums[i]) % p;
            int res = (n1 + p - n) % p;
            if (map.containsKey(res)) {
                min = Math.min(min, i - map.get(res));
            }
            map.put(n1, i);
        }

        return min == nums.length ? -1 : min;
    }
}
