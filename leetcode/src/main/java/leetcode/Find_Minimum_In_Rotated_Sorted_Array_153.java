package leetcode;

/**
 * 153. 寻找旋转排序数组中的最小值
 *
 * 题目描述：
 * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
 * 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2]。
 * 请找出其中最小的元素。
 *
 * 解题思路：
 * 使用二分查找，关键是判断最小值在哪一侧。
 * 通过比较 mid 和 right 的值来确定：
 * 1. nums[mid] > nums[right]：说明 mid 到 right 之间有断层（旋转点），最小值在右半部分
 * 2. nums[mid] <= nums[right]：说明 mid 到 right 单调递增，最小值在左半部分（包括 mid）
 *
 * 时间复杂度：O(log n)
 * 空间复杂度：O(1)
 */
public class Find_Minimum_In_Rotated_Sorted_Array_153 {

    public int findMin(int[] nums) {
        int left = 0, right = nums.length - 1;

        // 使用 left < right 而不是 left <= right
        // 因为 right = mid 时需要保留 mid，避免死循环
        while (left < right) {
            int mid = left + (right - left) / 2;

            // 关键：和 right 比较，不是和 left 比较
            // nums[mid] > nums[right]：断层在右边，最小值在 [mid+1, right]
            if (nums[mid] > nums[right]) {
                left = mid + 1;  // mid 不可能是最小值，排除
            } else {
                // nums[mid] <= nums[right]：mid 到 right 单调，最小值在 [left, mid]
                right = mid;  // mid 可能是最小值，保留
            }
        }

        // 退出循环时 left == right，即为最小值位置
        return nums[left];
    }

}
