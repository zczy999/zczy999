package ZuoGod.BinarySearch;

/**
 * LeetCode 34. 在排序数组中查找元素的第一个和最后一个位置
 *
 * 题目描述：
 * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。
 * 请你找出给定目标值在数组中的开始位置和结束位置。
 * 如果数组中不存在目标值 target，返回 [-1, -1]。
 *
 * 要求：时间复杂度 O(log n)
 *
 * 解题思路：
 * 1. 使用两次二分查找，分别找到目标值的左边界和右边界
 * 2. findFirst 方法找到第一个 >= target 的位置
 * 3. findLast 方法找到最后一个 <= target 的位置
 * 4. 验证找到的位置是否确实等于 target
 *
 * 时间复杂度：O(log n) - 两次二分查找
 * 空间复杂度：O(1) - 只使用常数额外空间
 */
public class Find_First_And_Last_Position_Of_Element_In_Sorted_Array_34 {

    /**
     * 在排序数组中查找目标值的起始和结束位置
     *
     * @param nums 非递减排序的整数数组
     * @param target 目标值
     * @return 目标值的起始和结束位置，如果不存在返回 [-1, -1]
     */
    public int[] searchRange(int[] nums, int target) {
        // 查找第一个位置（左边界）
        int left = findFirst(nums, target);
        // 查找最后一个位置（右边界）
        int right = findLast(nums, target);

        // 如果左边界或右边界无效，说明目标值不存在
        if (left == -1 || right == -1) {
            return new int[]{-1, -1};
        }

        // 验证找到的位置是否确实等于目标值
        if (nums[left] != target || nums[right] != target) {
            return new int[]{-1, -1};
        }

        return new int[]{left, right};
    }

    /**
     * 二分查找：找到最后一个 <= target 的位置（右边界）
     *
     * 核心思想：
     * - 当 nums[mid] <= target 时，记录当前位置，并继续向右查找
     * - 当 nums[mid] > target 时，向左收缩
     *
     * @param nums 排序数组
     * @param target 目标值
     * @return 最后一个 <= target 的位置，如果不存在返回 -1
     */
    private int findLast(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        int res = -1;  // 记录结果位置

        while (left <= right) {
            // 防止溢出的中点计算方式
            int mid = left + ((right - left) >> 1);

            if (nums[mid] <= target) {
                // 当前位置可能是答案，记录下来
                res = mid;
                // 继续向右查找，寻找更右边的位置
                left = mid + 1;
            } else {
                // nums[mid] > target，向左收缩
                right = mid - 1;
            }
        }
        return res;
    }

    /**
     * 二分查找：找到第一个 >= target 的位置（左边界）
     *
     * 核心思想：
     * - 当 nums[mid] >= target 时，记录当前位置，并继续向左查找
     * - 当 nums[mid] < target 时，向右收缩
     *
     * @param nums 排序数组
     * @param target 目标值
     * @return 第一个 >= target 的位置，如果不存在返回 -1
     */
    private int findFirst(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        int res = -1;  // 记录结果位置

        while (left <= right) {
            // 防止溢出的中点计算方式
            int mid = left + ((right - left) >> 1);

            if (nums[mid] >= target) {
                // 当前位置可能是答案，记录下来
                res = mid;
                // 继续向左查找，寻找更左边的位置
                right = mid - 1;
            } else {
                // nums[mid] < target，向右收缩
                left = mid + 1;
            }
        }
        return res;
    }
}
