package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 77. 组合
 *
 * 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
 * 你可以按 任何顺序 返回答案。
 *
 * 示例 1：
 * 输入：n = 3, k = 2
 * 输出：[[2,4],[3,4],[2,3],[1,2],[1,3],[1,4]]
 *
 * 示例 2：
 * 输入：n = 1, k = 1
 * 输出：[[1]]
 *
 * 提示：
 * - 1 <= n <= 20
 * - 1 <= k <= n
 *
 * 算法思路：回溯算法（深度优先搜索）
 * 1. 使用递归生成所有可能的组合
 * 2. 每次选择一个数字后，递归选择下一个数字
 * 3. 使用剪枝优化：当剩余数字不足以满足组合要求时提前返回
 * 4. 通过回溯（撤销选择）来生成所有可能的组合
 *
 * 时间复杂度：O(C(n,k) * k)，其中 C(n,k) 是组合数
 * 空间复杂度：O(k)，递归栈的深度
 */
public class Combinations_77 {

    /**
     * 存储所有可能的组合结果
     * 使用 ArrayList 存储动态增长的组合列表
     */
    private List<List<Integer>> result = new ArrayList<>();

    /**
     * 主方法：生成所有可能的组合
     *
     * @param n 数字范围的上限（1 到 n）
     * @param k 每个组合中数字的个数
     * @return 所有可能的 k 个数组合的列表
     */
    public List<List<Integer>> combine(int n, int k) {
        // 从数字 1 开始进行深度优先搜索
        dfs(n, k, 1, new ArrayList<Integer>());
        return result;
    }

    /**
     * 深度优先搜索（回溯算法）生成组合
     *
     * @param n 数字范围的上限
     * @param k 组合中需要的数字个数
     * @param i 当前可以开始选择的数字（确保组合的有序性，避免重复）
     * @param res 当前正在构建的组合
     */
    private void dfs(int n, int k, int i, ArrayList<Integer> res) {
        // 如果当前组合的大小达到要求，将其加入结果集
        // 注意：需要创建新的 ArrayList 副本，因为 res 会在回溯过程中被修改
        if (res.size() == k) {
            result.add(new ArrayList<>(res));
            // 注意：这里没有 return，因为下面的 for 循环条件会自然终止
        }

        // 剪枝条件：
        // 1. 如果当前起始数字已经超过 n，说明没有更多数字可选
        // 2. 如果当前组合大小已经超过 k，说明组合无效
        if (i > n || res.size() > k) {
            return;
        }

        // 尝试所有可能的下一个数字选择
        // 从 i 开始确保组合的递增性质，避免重复组合
        for (int j = i; j <= n; j++) {
            // 选择当前数字 j 加入组合
            res.add(j);

            // 递归调用，从 j+1 开始选择下一个数字（确保不重复使用）
            dfs(n, k, j + 1, res);

            // 回溯：移除最后加入的数字，尝试其他选择
            // 这是回溯算法的关键步骤，恢复状态以便探索其他可能性
            res.remove(res.size() - 1);
        }
    }

}
