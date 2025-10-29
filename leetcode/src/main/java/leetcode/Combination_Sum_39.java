package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 39. 组合总和
 *
 * 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target，
 * 找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合，
 * 并以列表形式返回。你可以按 任意顺序 返回这些组合。
 *
 * candidates 中的 同一个 数字可以 无限制重复被选取。
 * 如果至少一个数字的被选数量不同，则两种组合是不同的。
 *
 * 示例 1：
 * 输入：candidates = [2,3,6,7], target = 7
 * 输出：[[2,2,3],[7]]
 *
 * 示例 2：
 * 输入：candidates = [2,3,5], target = 8
 * 输出：[[2,2,2,2],[2,3,3],[3,5]]
 *
 * 算法思路：回溯算法（深度优先搜索）
 * 1. 使用递归生成所有可能的组合
 * 2. 每个数字可以被重复使用，所以递归时可以保持当前索引
 * 3. 使用剪枝优化：当和超过目标值时提前返回
 * 4. 通过回溯（撤销选择）来生成所有可能的组合
 *
 * 注意：当前实现存在重复问题，需要先排序并优化去重逻辑
 *
 * 时间复杂度：O(N^(T/min + 1))，其中 N 是候选数组长度，T 是目标值，min 是最小候选数
 * 空间复杂度：O(T/min)，递归栈的最大深度
 */
public class Combination_Sum_39 {

    /**
     * 存储所有可能的组合结果
     * 使用 ArrayList 存储动态增长的组合列表
     */
    private List<List<Integer>> res = new ArrayList<>();

    /**
     * 主方法：找出所有可能的组合
     *
     * @param candidates 候选数字数组（无重复元素）
     * @param target 目标和
     * @return 所有可能的组合列表
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        // 从索引 0 开始进行深度优先搜索，初始和为 0
        dfs(candidates, target, 0, 0, new ArrayList<>());
        return res;
    }

    /**
     * 深度优先搜索（回溯算法）生成组合
     *
     * @param candidates 候选数字数组
     * @param target 目标和
     * @param sum 当前路径中数字的总和
     * @param cur 当前考虑的数字索引
     * @param path 当前正在构建的组合路径
     */
    private void dfs(int[] candidates, int target, int sum, int cur, List<Integer> path) {
        // 剪枝条件：
        // 1. 如果当前和超过目标值，说明这条路径无效，直接返回
        // 2. 如果当前索引已经超出数组范围，说明没有更多数字可选
        if (sum > target || cur == candidates.length) {
            return;
        }

        // 如果当前和正好等于目标值，找到有效组合
        // 需要创建新的 ArrayList 副本，因为 path 会在回溯过程中被修改
        if (sum == target) {
            res.add(new ArrayList<>(path));
            return; // 找到解后立即返回，避免继续搜索
        }

        // 选择当前数字 candidates[cur] 加入路径
        path.add(candidates[cur]);

        // 递归调用，继续从当前索引开始（因为可以重复使用同一个数字）
        // 注意这里传的是 cur 而不是 cur + 1，允许重复选择当前数字
        dfs(candidates, target, sum + candidates[cur], cur, path);

        // 回溯：移除最后加入的数字，尝试其他选择
        // 这是回溯算法的关键步骤，恢复状态以便探索其他可能性
        path.remove(path.size() - 1);

        // 不选择当前数字，直接跳到下一个数字
        // 这样可以探索不包含当前数字的组合
        dfs(candidates, target, sum, cur + 1, path);
    }

}
