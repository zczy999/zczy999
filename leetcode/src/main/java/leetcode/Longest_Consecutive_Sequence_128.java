package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * LeetCode 128. 最长连续序列
 *
 * 给定一个未排序的整数数组 nums，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
 *
 * 示例 1：
 * 输入：nums = [100,4,200,1,3,2]
 * 输出：4
 * 解释：最长数字连续序列是 [1,2,3,4]。它的长度为 4。
 *
 * 示例 2：
 * 输入：nums = [0,3,7,2,5,8,4,6,0,1]
 * 输出：9
 *
 * @author Claude Code
 * @version 1.0
 */
public class Longest_Consecutive_Sequence_128 {

    /**
     * 方法一：使用 HashSet 查找序列起点
     *
     * 算法思路：
     * 1. 将所有数字存入 HashSet，实现 O(1) 时间复杂度的查找
     * 2. 遍历每个数字，只有当它是序列起点时才计算序列长度
     * 3. 序列起点的判断条件：该数字减 1 不在集合中
     * 4. 从起点开始向右查找连续数字，计算序列长度
     *
     * 时间复杂度：O(n) - 每个数字最多被访问两次
     * 空间复杂度：O(n) - HashSet 存储所有数字
     *
     * @param nums 未排序的整数数组
     * @return 最长连续序列的长度
     */
    public int longestConsecutive(int[] nums) {
        // 将所有数字存入 HashSet，便于快速查找
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int ans = 0;

        // 遍历集合中的每个数字
        for (int num : set) {
            // 只有当 num 是序列起点时才开始计算
            // 序列起点的定义：num - 1 不在集合中
            if (!set.contains(num - 1)) {
                int cur = num;  // 当前数字

                // 向右查找连续的数字
                while (set.contains(cur + 1)){
                    cur++;
                }

                // 计算当前序列长度：cur - num + 1
                ans = Math.max(ans, cur - num + 1);
            }
        }

        return ans;
    }

    /**
     * 方法二：使用 HashMap 并查集思想
     *
     * 算法思路：
     * 1. 使用 HashMap 存储每个数字所在连续序列的长度
     * 2. 遍历每个数字，查找其左右相邻序列的长度
     * 3. 合并左右序列，形成新的连续序列
     * 4. 更新新序列左右边界的长度值
     *
     * 关键优化：
     * - 只在序列边界存储长度信息，减少存储空间
     * - 重复数字直接跳过，避免重复计算
     * - 动态更新序列边界，实现快速合并
     *
     * 时间复杂度：O(n) - 每个数字只处理一次
     * 空间复杂度：O(n) - HashMap 存储边界信息
     *
     * @param nums 未排序的整数数组
     * @return 最长连续序列的长度
     */
    public int longestConsecutive1(int[] nums) {
        // HashMap 存储数字到其所在序列长度的映射
        Map<Integer, Integer> map = new HashMap<>();
        int ans = 0;

        // 遍历每个数字
        for (int num : nums) {
            // 跳过重复数字，避免重复计算
            if (map.containsKey(num)) continue;

            // 查找左右相邻序列的长度
            int left = map.getOrDefault(num - 1, 0);   // 左边序列长度
            int right = map.getOrDefault(num + 1, 0);  // 右边序列长度

            // 当前数字连接左右序列后的总长度
            int cur = left + 1 + right;

            // 更新当前数字的序列长度
            map.put(num, cur);

            // 更新新序列的左边界长度
            // num - left 是新序列的最左端数字
            map.put(num - left, cur);

            // 更新新序列的右边界长度
            // num + right 是新序列的最右端数字
            map.put(num + right, cur);

            // 更新全局最大值
            ans = Math.max(ans, cur);
        }

        return ans;
    }
}
