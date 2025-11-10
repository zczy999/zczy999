package leetcode;

/**
 * 169. 多数元素
 *
 * 解法1：摩尔投票算法（Boyer-Moore Voting Algorithm）
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 解法2：分治法
 * 时间复杂度：O(n log n)
 * 空间复杂度：O(log n) 递归栈
 */
public class Majority_Element_169 {

    /**
     * 解法1：摩尔投票算法（Boyer-Moore Voting Algorithm）
     *
     * 核心思想：
     * 1. 维护一个候选元素 cur 和它的计数 count
     * 2. 遍历数组：
     *    - 如果 count == 0，更换候选元素为当前元素
     *    - 如果当前元素等于候选元素，count++
     *    - 如果当前元素不等于候选元素，count--
     * 3. 最后剩下的候选元素就是多数元素
     *
     * 正确性证明：
     * - 多数元素出现次数 > n/2
     * - 即使所有非多数元素都抵消多数元素，多数元素仍会剩余
     * - 因为多数元素比其他所有元素加起来还多
     *
     * 示例：[2,2,1,1,1,2,2]
     * - i=0: cur=2, count=1
     * - i=1: cur=2, count=2 (遇到相同)
     * - i=2: cur=2, count=1 (遇到不同，抵消)
     * - i=3: cur=2, count=0 (遇到不同，抵消)
     * - i=4: cur=1, count=1 (count==0，更换候选)
     * - i=5: cur=1, count=0 (遇到不同，抵消)
     * - i=6: cur=2, count=1 (count==0，更换候选)
     * - 返回 2
     *
     * @param nums 数组
     * @return 多数元素
     */
    public int majorityElement(int[] nums) {
        int cur = nums[0];  // 当前候选元素
        int count = 1;      // 候选元素的计数

        for (int i = 1; i < nums.length; i++) {
            // 计数归零，更换候选元素
            if (count == 0) {
                cur = nums[i];
                count = 1;
                continue;
            }
            // 遇到不同元素，抵消计数
            if (nums[i] != cur) {
                count--;
            } else {
                // 遇到相同元素，增加计数
                count++;
            }
        }
        return cur;
    }


    /**
     * 解法2：分治法
     *
     * @param nums 数组
     * @return 多数元素
     */
    public int majorityElement1(int[] nums) {
        return f(nums, 0, nums.length - 1);
    }

    /**
     * 分治递归函数：在 [l, r] 区间内找多数元素
     *
     * 递归边界分析：
     * - 当 l == r 时，单个元素直接返回（递归终止）
     * - 当 l < r 时，mid = (l+r)/2，由于整数除法 mid < r
     *   因此 mid 最大为 r-1（整数性质）
     *   - 左半部分 [l, mid]：l <= mid 必然成立
     *   - 右半部分 [mid+1, r]：mid+1 <= r 必然成立
     * - 不会出现 l > r 的情况，无需额外判断
     *
     * @param nums 原数组
     * @param l    左边界
     * @param r    右边界
     * @return 多数元素
     */
    private int f(int[] nums, int l, int r) {
        // 递归终止条件：单个元素一定是多数元素
        if (l == r) {
            return nums[l];
        }

        // 分治：拆分成左右两部分
        int mid = (l + r) / 2;
        int leftNum = f(nums, l, mid);      // 左半部分的多数元素候选
        int rightNum = f(nums, mid + 1, r); // 右半部分的多数元素候选

        // 如果左右两边的多数元素相同，直接返回
        if (leftNum == rightNum) {
            return leftNum;
        }

        // 否则需要验证哪个候选是整个区间的多数元素
        // 统计左侧候选在整个区间的出现次数
        int count = count(nums, leftNum, l, r);
        if (count > (r - l + 1) / 2) {
            return leftNum;
        }
        // 左侧候选不满足，右侧候选一定满足（题目保证多数元素存在）
        return rightNum;
    }

    /**
     * 统计 num 在 [l, r] 区间内的出现次数
     */
    private int count(int[] nums, int num, int l, int r) {
        int count = 0;
        for (int i = l; i <= r; i++) {
            if (nums[i] == num) {
                count++;
            }
        }
        return count;
    }
}
