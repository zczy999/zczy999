package leetcode;

public class Two_Sum_II_167 {

    /**
     * 二分法
     *
     * @param numbers
     * @param target
     * @return
     */
    public int[] twoSum(int[] numbers, int target) {
        int[] res = new int[]{-1, -1};
        for (int i = 0; i < numbers.length; i++) {
            int left = i;
            int right = numbers.length - 1;
            int num = target - numbers[i];
            while (left + 1 < right) {
                int mid = (left + right) / 2;
                if (numbers[mid] < num) {
                    left = mid;
                } else if (numbers[mid] == num) {
                    res[0] = i + 1;
                    res[1] = mid + 1;
                    return res;
                } else {
                    right = mid;
                }
            }
            if (numbers[right] == num) {
                res[0] = i + 1;
                res[1] = right + 1;
                return res;
            }
            if (numbers[left] == num) {
                res[0] = i + 1;
                res[1] = left + 1;
                return res;
            }

        }
        return res;
    }

    /**
     * 相向双指针
     *
     * @param numbers
     * @param target
     * @return
     */
    public int[] twoSum1(int[] numbers, int target) {
        int[] res = new int[2];
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            if (numbers[left] + numbers[right] < target) {
                left++;
            } else if (numbers[left] + numbers[right] == target) {
                res[0] = left + 1;
                res[1] = right + 1;
                return res;
            } else {
                right--;
            }
        }
        return res;
    }

    public int[] twoSum2(int[] numbers, int target) {
        int[] res = new int[2];
        int left = 0, right = 1;
        while (left < numbers.length) {
            while (right < numbers.length) {
                if (numbers[left] + numbers[right] < target) {
                    right++;
                } else if (numbers[left] + numbers[right] == target) {
                    res[0] = left + 1;
                    res[1] = right + 1;
                    return res;
                }
            }
            left++;
            right = left + 1;
        }
        return res;
    }
}
