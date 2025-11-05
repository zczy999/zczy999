package leetcode;

/**
 * LeetCode 80. 删除排序数组中的重复项 II
 *
 * 题目描述：
 * 给你一个有序数组 nums ，请你原地删除重复出现的元素，使得每个元素最多出现两次，返回删除后数组的新长度。
 * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
 *
 * 示例：
 * 输入：nums = [1,1,1,2,2,3]
 * 输出：5, nums = [1,1,2,2,3,_]
 *
 * 算法思路：
 * 1. 使用双指针法，index 指向下一个要填充的位置
 * 2. count 记录当前元素出现的次数
 * 3. 遍历数组，根据当前元素与前一个元素的关系决定是否保留：
 *    - 如果当前元素与前一个不同，重置 count 为 1，保留该元素
 *    - 如果相同且 count < 2，保留该元素，count++
 *    - 如果相同且 count >= 2，跳过该元素（不保留）
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class Remove_Duplicates_From_Sorted_Array_II_80 {

    public int removeDuplicates(int[] nums) {
        // index 指向下一个要填充的位置
        int index = 1;
        // count 记录当前元素的出现次数
        int count = 1;

        // 从第二个元素开始遍历
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                // 当前元素与前一个不同，重置计数为 1
                count = 1;
                nums[index++] = nums[i];
            } else if (count < 2) {
                // 当前元素与前一个相同，但出现次数小于 2，可以保留
                nums[index++] = nums[i];
                count++;
            } else {
                // 当前元素与前一个相同，且已经出现 2 次或以上，跳过
                count++;
            }
        }

        // 返回新数组的长度
        return index;
    }

}
