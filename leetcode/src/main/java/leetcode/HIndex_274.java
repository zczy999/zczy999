package leetcode;

import java.util.Arrays;

/**
 * LeetCode 274. H-Index (H指数)
 *
 * 题目描述：
 * 给定一个研究者的论文引用次数数组 citations，其中 citations[i] 表示第 i 篇论文的引用次数。
 * 计算并返回该研究者的 h 指数。
 *
 * h 指数定义：h 代表"高引用次数"，一名科研人员的 h 指数是指他（她）的 N 篇论文中
 * 总共有 h 篇论文分别被引用了至少 h 次，其余的 N - h 篇论文每篇被引用次数不超过 h 次。
 *
 * 示例：
 * 输入：citations = [3,0,6,1,5]
 * 输出：3
 * 解释：给定数组表示研究者总共有 5 篇论文，每篇论文相应的被引用了 3,0,6,1,5 次。
 *      由于研究者有 3 篇论文每篇至少被引用了 3 次，其余 2 篇论文每篇被引用不多于 3 次，所以她的 h 指数是 3。
 */
public class HIndex_274 {

    /**
     * 方法一：二分答案法（左神做法）
     *
     * 核心思想：
     * H指数的范围是 [0, n]，其中 n 是论文总数。
     * 我们可以在这个范围内二分搜索，对于每个候选值 mid，
     * 检查是否至少有 mid 篇论文的引用次数 >= mid。
     *
     * 算法流程：
     * 1. 设定搜索区间 [0, citations.length]
     * 2. 对于每个中间值 mid，统计引用次数 >= mid 的论文数量
     * 3. 如果满足条件的论文数量 >= mid，说明 h 指数至少为 mid，尝试更大的值
     * 4. 否则说明 mid 太大了，需要缩小搜索范围
     *
     * 时间复杂度：O(n*log(n))，二分搜索 O(log(n))，每次需要遍历数组 O(n)
     * 空间复杂度：O(1)
     *
     * @param citations 论文引用次数数组
     * @return h 指数
     */
    public int hIndex(int[] citations) {
        int left = 0, right = citations.length;  // 二分搜索区间：[0, n]
        int res = 0;  // 记录满足条件的最大 h 值

        while (left <= right) {
            int mid = (left + right) / 2;  // 当前尝试的 h 值

            // 检查是否至少有 mid 篇论文的引用次数 >= mid
            if (f(mid, citations) >= mid) {
                res = mid;  // 满足条件，记录当前 h 值
                left = mid + 1;  // 尝试更大的 h 值
            } else {
                right = mid - 1;  // 不满足条件，缩小 h 值
            }
        }
        return res;
    }

    /**
     * 辅助函数：统计引用次数 >= mid 的论文数量
     *
     * @param mid 当前尝试的 h 值
     * @param citations 论文引用次数数组
     * @return 引用次数 >= mid 的论文数量
     */
    private int f(int mid, int[] citations) {
        int count = 0;
        for (int i = 0; i < citations.length; i++) {
            if (citations[i] >= mid) {  // 统计引用次数达到 mid 的论文
                count++;
            }
        }
        return count;
    }

    /**
     * 方法二：计数排序法（最优解法）
     *
     * 核心思想：
     * 使用计数数组统计每个引用次数对应的论文数量，然后从高到低累加，
     * 找到第一个满足"至少有 h 篇论文被引用至少 h 次"的 h 值。
     *
     * 算法流程：
     * 1. 创建计数数组 res[n+1]，res[i] 表示引用次数为 i 的论文数量
     * 2. 遍历 citations，统计每个引用次数的论文数量：
     *    - 如果引用次数 >= n，统一放在 res[n] 位置（因为 h 指数最大为 n）
     *    - 否则放在 res[citations[i]] 位置
     * 3. 从 n 到 1 倒序遍历，累加引用次数 >= i 的论文总数 total
     * 4. 当 total >= i 时，说明至少有 i 篇论文被引用了至少 i 次，返回 i
     *
     * 示例：citations = [3,0,6,1,5]，n=5
     * - res = [1,1,0,1,0,2]（res[5]=2 表示引用次数>=5的有2篇）
     * - 从后向前遍历：
     *   i=5: total=2, 2>=5? 否
     *   i=4: total=2, 2>=4? 否
     *   i=3: total=3, 3>=3? 是，返回3
     *
     * 时间复杂度：O(n)，只需遍历两次数组
     * 空间复杂度：O(n)，需要额外的计数数组
     *
     * @param citations 论文引用次数数组
     * @return h 指数
     */
    public static int hIndex1(int[] citations) {
        int len = citations.length;
        int total = 0;  // 累计引用次数 >= i 的论文总数
        int[] res = new int[citations.length + 1];  // 计数数组，res[i] 表示引用次数为 i 的论文数

        // 第一步：统计每个引用次数对应的论文数量
        for (int i = 0; i < citations.length; i++) {
            if (citations[i] >= citations.length) {
                // 引用次数 >= n 的论文统一放在 res[n]，因为 h 指数最大只能是 n
                res[citations.length]++;
                continue;
            }
            res[citations[i]]++;  // 引用次数为 citations[i] 的论文数量 +1
        }

        // 第二步：从高到低累加，找到满足条件的最大 h 值
        for (int i = citations.length; i > 0; i--) {
            total += res[i];  // 累加引用次数 >= i 的论文数量
            // 关键判断：如果至少有 i 篇论文被引用了至少 i 次，返回 i
            // total 表示引用次数 >= i 的论文总数，i 表示当前候选的 h 值
            if (i <= total) {
                return i;
            }
        }
        return 0;  // 所有论文引用次数都为 0 的情况

    }

    public static void main(String[] args) {
        int[] citations = {3, 0, 6, 1, 5};
        int i = HIndex_274.hIndex1(citations);
        System.out.println(i);
    }


}
