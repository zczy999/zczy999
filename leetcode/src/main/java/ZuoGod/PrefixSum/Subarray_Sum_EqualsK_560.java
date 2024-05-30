package ZuoGod.PrefixSum;

import java.util.HashMap;
import java.util.Map;

public class Subarray_Sum_EqualsK_560 {
    public int subarraySum(int[] nums, int k) {
        int[] preSum = new int[nums.length + 1];
        Map<Integer, Integer> map = new HashMap<>();

        map.put(0, 1);
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            preSum[i + 1] = preSum[i] + nums[i];
            int res = preSum[i + 1] - k;
            sum += map.getOrDefault(res, 0);

            map.put(preSum[i + 1], map.getOrDefault(preSum[i + 1], 0) + 1);
        }

        return sum;
    }

}
