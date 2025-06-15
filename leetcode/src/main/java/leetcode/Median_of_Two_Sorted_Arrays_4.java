package leetcode;

public class Median_of_Two_Sorted_Arrays_4 {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        int len1 = nums1.length;
        int len2 = nums2.length;

        // 保证 nums1 是较短数组，以便后续在其上进行对数级二分搜索
        if (len1 > len2) {
            return findMedianSortedArrays(nums2, nums1);
        }
        int len = len1 + len2;
        double ans = 0;

        // k 表示合并后整体左半部分元素的数量 (奇偶皆适用)
        int k = (len + 1) / 2;
        ans = findKth(nums1, nums2, k); // 调用辅助函数查找第 k 小元素
        return ans;
    }

    /**
     * 在两个升序数组中寻找整体第 k 小的元素。
     * 只在较短的 nums1 上执行二分，时间复杂度 O(log(min(len1, len2)))，空间 O(1)。
     */
    private double findKth(int[] nums1, int[] nums2, int k) {
        // 二分范围：[0, nums1.length]，含义是“切口 i 可以把 nums1 的前 i 个放到左区”
        int nums1LeftIndex = 0, nums1RightIndex = nums1.length;

        while (nums1LeftIndex <= nums1RightIndex) {
            // i = nums1 左区元素个数，j = nums2 左区元素个数
            int i = nums1LeftIndex + (nums1RightIndex - nums1LeftIndex) / 2;
            int j = k - i;

            // 处理边界：若切口在最左/最右，用哨兵值 ±∞ 避免越界比较
            int nums1Left = i == 0 ? Integer.MIN_VALUE : nums1[i - 1];
            int nums1Right = i == nums1.length ? Integer.MAX_VALUE : nums1[i];
            int nums2Left = j == 0 ? Integer.MIN_VALUE : nums2[j - 1];
            int nums2Right = j == nums2.length ? Integer.MAX_VALUE : nums2[j];

            // 检查切口是否满足：左区最大 <= 右区最小
            if (nums1Left <= nums2Right && nums2Left <= nums1Right) {
                // 切口成功，依据总长度奇偶返回结果
                if ((nums1.length + nums2.length) % 2 == 1) {
                    // 奇数：直接返回左区最大
                    return Math.max(nums1Left, nums2Left);
                } else {
                    // 偶数：返回左右区中间两数的平均
                    return (Math.max(nums1Left, nums2Left) + Math.min(nums1Right, nums2Right)) / 2.0;
                }

            } else if (nums1Left > nums2Right) {
                // i 取大了，左区的 nums1Left 太大 → 切口左移
                nums1RightIndex = i - 1;
            } else {
                // i 取小了，右区的 nums2Left 太大 → 切口右移
                nums1LeftIndex = i + 1;
            }
        }
        return -1; // 理论上不会到达这里
    }


}
