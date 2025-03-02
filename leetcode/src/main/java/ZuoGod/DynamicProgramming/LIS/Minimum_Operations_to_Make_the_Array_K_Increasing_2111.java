package ZuoGod.DynamicProgramming.LIS;

public class Minimum_Operations_to_Make_the_Array_K_Increasing_2111 {

    public static void main(String[] args) {
        Minimum_Operations_to_Make_the_Array_K_Increasing_2111 res = new Minimum_Operations_to_Make_the_Array_K_Increasing_2111();
        int[] nums = {4, 1, 5, 2, 6, 2};
        System.out.println(res.kIncreasing(nums, 2));
    }

    public int kIncreasing(int[] nums, int k) {

        int time = 0;
        int res = 0;
        while (time < k) {
            int[] end = new int[(nums.length + k - 1) / k];
            end[0] = nums[time];
            int increasingLen = 1;
            int len = 1;
            for (int i = time + k; i < nums.length; i = i + k) {
                len++;
                int rightPos = findRightPos(end, increasingLen, nums[i]);
                if (rightPos == -1) {
                    end[increasingLen++] = nums[i];
                } else {
                    end[rightPos] = nums[i];
                }

            }
            res += len - increasingLen;
            time++;
        }

        return res;
    }

    private int findRightPos(int[] end, int len, int target) {
        int ans = -1;
        int left = 0, right = len - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (end[mid] > target) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

}
