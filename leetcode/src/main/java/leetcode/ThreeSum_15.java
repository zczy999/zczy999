package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ThreeSum_15 {

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            //判断重复
            if (i > 0 && nums[i - 1] == nums[i]) {
                continue;
            }

            int left = i + 1;
            int right = nums.length - 1;
            int res = -nums[i];
            while (left < right) {
                //判断重复
                if (left > (i + 1) && nums[left - 1] == nums[left]) {
                    left++;
                    continue;
                }

                //
                while (left < right && nums[left] + nums[right] > res) {
                    right--;
                }

                if (left == right) {
                    break;
                }

                if (nums[left] + nums[right] == res) {
                    List<Integer> list = new ArrayList<>();
                    list.add(-res);
                    list.add(nums[left]);
                    list.add(nums[right]);
                    ans.add(list);
                }
                left++;

            }
        }
        return ans;

    }

    public List<List<Integer>> threeSum1(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        int left = 0, right = nums.length - 1;
        while (left < nums.length && right >= 0 && nums[left] <= 0 && nums[right] >= 0) {
            List<Integer> list = new ArrayList<>();
            list.add(nums[left]);
            list.add(nums[right]);
            int temp = nums[left] + nums[right];
            if (temp <= 0) {
                for (int i = right - 1; i > left && nums[i] >= 0; i--) {
                    if (nums[i] + temp == 0) {
                        list.add(nums[i]);
                        res.add(list);
                        break;
                    }
                }
                left++;
            } else {
                for (int i = left + 1; i < right && nums[i] < 0; i++) {
                    if (nums[i] + temp == 0) {
                        list.add(nums[i]);
                        res.add(list);
                        break;
                    }
                }
                right--;
            }
        }
        return res.stream().distinct().collect(Collectors.toList());
    }

}
