package leetcode;

import java.util.ArrayList;
import java.util.List;

public class Summary_Ranges_228 {
    public List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();
        if (nums.length == 1) {
            res.add(Integer.toString(nums[0]));
            return res;
        }
        int left = 0;
        for (int right = 1; right <= nums.length; right++) {
            if (right == nums.length){
                extracted(nums, left, right, res);
                break;
            }
            if (nums[right] - nums[right - 1] == 1) {
                continue;
            }
            extracted(nums, left, right, res);
            left = right;
        }
        return res;

    }

    private void extracted(int[] nums, int left, int right, List<String> res) {
        if (left == right - 1){
            res.add(Integer.toString(nums[left]));

        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.toString(nums[left]));
            sb.append("->");
            sb.append(Integer.toString(nums[right -1]));
            res.add(sb.toString());
        }
    }
}
