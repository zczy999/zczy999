package leetcode;

/**
 * 238. 除自身以外数组的乘积
 *
 * 题目描述：
 * 给你一个整数数组 nums，返回数组 answer，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积。
 * 题目数据保证数组 nums 之中任意元素的全部前缀元素和后缀元素的乘积都在 32 位整数范围内。
 * 请不要使用除法，且在 O(n) 时间复杂度内完成此题。
 *
 * 示例：
 * 输入: nums = [1,2,3,4]
 * 输出: [24,12,8,6]
 * 解释: 24 = 2*3*4, 12 = 1*3*4, 8 = 1*2*4, 6 = 1*2*3
 */
public class Product_of_Array_Except_Self_238 {

    /**
     * 左右乘积列表法
     *
     * 算法思路：
     * 1. 对于索引 i，answer[i] = (i左边所有元素的乘积) × (i右边所有元素的乘积)
     * 2. 使用两个辅助数组：
     *    - L[i]：表示索引 i 左侧所有元素的乘积
     *    - R[i]：表示索引 i 右侧所有元素的乘积
     * 3. 最终结果 res[i] = L[i] × R[i]
     *
     * 时间复杂度：O(n)，需要遍历数组三次
     * 空间复杂度：O(n)，使用了两个长度为 n 的辅助数组（不包括结果数组）
     *
     * @param nums 输入数组
     * @return 除自身外其余元素的乘积数组
     */
    public int[] productExceptSelf(int[] nums) {
        // L[i] 表示索引 i 左侧所有元素的乘积
        int[] L = new int[nums.length];
        // R[i] 表示索引 i 右侧所有元素的乘积
        int[] R = new int[nums.length];

        // 初始化：L[0] 左侧没有元素，乘积为 1
        L[0] = 1;
        // 从左到右遍历，计算每个位置左侧所有元素的乘积
        for (int i = 1; i < nums.length; i++) {
            // L[i] = 前一个位置的左侧乘积 × 前一个元素
            L[i] = L[i-1] * nums[i - 1];
        }

        // 初始化：R[n-1] 右侧没有元素，乘积为 1
        R[nums.length - 1] = 1;
        // 从右到左遍历，计算每个位置右侧所有元素的乘积
        for (int j = nums.length - 2; j >= 0; j--) {
            // R[j] = 后一个位置的右侧乘积 × 后一个元素
            R[j] = R[j+1] * nums[j + 1];
        }

        // 构造结果数组
        int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            // 每个位置的结果 = 左侧乘积 × 右侧乘积
            res[i] = L[i] * R[i];
        }
        return res;
    }

    /**
     * 空间优化版本：O(1) 额外空间复杂度
     *
     * 算法思路：
     * 1. 直接在输出数组上操作，不使用额外的 L 和 R 数组
     * 2. 第一步：res[i] 先存储 i 左侧所有元素的乘积
     * 3. 第二步：用变量 R 记录右侧累积乘积，从右向左遍历更新 res[i]
     *    - res[i] = res[i] (左侧乘积) × R (右侧乘积)
     *    - 同时更新 R = R × nums[i]，为下一次迭代准备
     *
     * 优化点：
     * - 复用输出数组存储左侧乘积，避免额外的 L 数组
     * - 用单个变量 R 替代整个 R 数组，边遍历边累积右侧乘积
     *
     * 示例：nums = [1,2,3,4]
     * 第一步后：res = [1, 1, 2, 6]  (存储左侧乘积)
     * 第二步：
     *   i=3: res[3] = 6*1=6,   R=4
     *   i=2: res[2] = 2*4=8,   R=12
     *   i=1: res[1] = 1*12=12, R=24
     *   i=0: res[0] = 1*24=24, R=24
     * 最终：res = [24,12,8,6]
     *
     * 时间复杂度：O(n)，两次遍历
     * 空间复杂度：O(1)，只使用了常数个变量（不包括输出数组）
     *
     * @param nums 输入数组
     * @return 除自身外其余元素的乘积数组
     */
    public int[] productExceptSelfOptimized(int[] nums) {
        int[] res = new int[nums.length];

        // 第一步：res[i] 存储左侧所有元素的乘积
        // res[0] = 1，因为索引 0 左侧没有元素
        res[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            // res[i] = 左侧所有元素的乘积 = res[i-1] × nums[i-1]
            res[i] = res[i - 1] * nums[i - 1];
        }

        // 第二步：从右向左遍历，用变量 R 记录右侧累积乘积
        // R 表示当前位置右侧所有元素的乘积
        int R = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            // res[i] 当前存储的是左侧乘积，乘以右侧乘积 R 得到最终结果
            res[i] = res[i] * R;
            // 更新 R：将当前元素纳入右侧乘积，为下一次迭代（i-1）准备
            R = R * nums[i];
        }

        return res;
    }
}
