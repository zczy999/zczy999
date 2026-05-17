package ZuoGod.SlidingWindow;

/**
 * LeetCode 209 - 长度最小的子数组
 * <p>
 * 题目描述：
 * 给定一个含有 n 个【正整数】的数组 nums 和一个正整数 target，
 * 找到该数组中满足累加和 >= target 的【长度最小】的连续子数组，并返回其长度。
 * 如果不存在符合条件的子数组，返回 0。
 * <p>
 * 示例：
 *   输入: target = 7, nums = [2,3,1,2,4,3]
 *   输出: 2
 *   解释: 子数组 [4,3] 是该条件下的长度最小的子数组
 * <p>
 * 核心思路：滑动窗口（双指针）
 * 由于元素全是正整数，窗口扩大则 sum 单调递增，窗口缩小则 sum 单调递减，
 * 这种单调性是滑动窗口能够工作的前提（若含负数，需改用前缀和 + 其他技巧）。
 * <p>
 * 复杂度分析：
 *   时间复杂度 O(n)：左右指针都只向右移动，最多各走 n 步
 *   空间复杂度 O(1)：只使用常数个变量
 * <p>
 * 测试链接：<a href="https://leetcode.cn/problems/minimum-size-subarray-sum/">LeetCode 209</a>
 */
public class Minimum_Size_Subarray_Sum_209 {

    /**
     * 解法一：经典滑动窗口 —— "够了就缩到刚好不够"
     * <p>
     * 模板套路：
     *   1. 右指针 r 不断向右扩张窗口，累加 nums[r] 到 sum
     *   2. 当窗口和 sum >= target 时，进入收缩循环：
     *      - 先更新答案（当前窗口已经满足条件）
     *      - 再缩小左边界，看看是否还能更短
     *   3. 直到 sum < target 才停止收缩，继续扩张右边界
     * <p>
     * 特点：在 while 内部更新 ans，每次窗口满足条件都会比较一次最小值
     */
    public static int minSubArrayLen1(int target, int[] nums) {
        int ans = Integer.MAX_VALUE;
        // l：窗口左边界  r：窗口右边界  sum：窗口 [l..r] 的累加和
        for (int l = 0, r = 0, sum = 0; r < nums.length; r++) {
            sum += nums[r]; // 扩张：把 nums[r] 纳入窗口
            // 只要窗口达标，就尝试收缩左边界，寻找更短的合法窗口
            while (sum >= target) {
                ans = Math.min(ans, r - l + 1); // 当前窗口长度
                sum -= nums[l]; // 把 nums[l] 移出窗口
                l++;            // 左边界右移
            }
        }
        // 若答案从未被更新过，说明整个数组累加和都 < target，返回 0
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }


    /**
     * 解法二：滑动窗口优化版 —— "提前缩到边界，再统一判断"
     * <p>
     * 优化思路：
     *   - 解法一每次满足条件都要进 while 内多次更新 ans
     *   - 解法二只在 r 走完一步后，把左边界缩到"再缩一格就不达标"的极限位置
     *   - 然后用一次 if 判断当前窗口是否达标，达标才更新 ans
     * <p>
     * 关键技巧：
     *   while 的条件是 sum - nums[l] >= target，
     *   含义是"假设把 nums[l] 移出窗口后，剩下的部分依然 >= target"，
     *   那么 nums[l] 就是"多余"的，可以安全地移出去 —— 因为我们要找最短的窗口。
     * <p>
     * 优势：减少 Math.min 的调用次数，常数级别的性能提升
     */
    public static int minSubArrayLen(int target, int[] nums) {
        int ans = Integer.MAX_VALUE;
        for (int l = 0, r = 0, sum = 0; r < nums.length; r++) {
            sum += nums[r]; // 扩张：把 nums[r] 纳入窗口
            // sum 表示当前窗口 nums[l..r] 的累加和
            // 如果 l 位置的数从窗口出去后，剩余和仍然达标，说明 nums[l] 是冗余的，移出
            while (sum - nums[l] >= target) {
                sum -= nums[l++];
            }
            // 此时窗口左边界已经被缩到极限：再缩一格就不达标
            // 但当前 sum 不一定 >= target（可能整个窗口和都不够），所以需要判断
            if (sum >= target) {
                ans = Math.min(ans, r - l + 1);
            }
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }

}
