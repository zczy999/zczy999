package leetcode;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class Snakes_And_Ladders_909 {


    /**
     * 蛇与梯子游戏 - BFS算法实现
     *
     * 算法思路：使用广度优先搜索逐层探索，保证第一次到达终点就是最短路径
     * 使用队列存储每一层要探索的位置，使用Set记录已访问位置避免重复
     *
     * 时间复杂度：O(n²)，其中n是棋盘边长
     * 空间复杂度：O(n²)
     *
     * @param board 二维棋盘，-1表示普通格子，其他数字表示蛇或梯子的目标位置
     * @return 最少移动次数，如果无法到达则返回-1
     */
    public int snakesAndLadders(int[][] board) {
        Deque<Integer> queue = new ArrayDeque<>();  // BFS队列，存储待探索的位置
        queue.offer(1);  // 起始位置为1

        int nums = board.length * board[0].length;  // 棋盘总格子数
        Set<Integer> visited = new HashSet<>();    // 记录已访问的位置，避免重复探索

        int level = 0;  // 当前探索的层数，即投掷骰子的次数

        // BFS主循环
        while (!queue.isEmpty()) {
            int size = queue.size();  // 当前层的节点数量

            // 处理当前层的所有节点
            for (int i = 0; i < size; i++) {
                int cur = queue.poll();  // 取出当前位置

                // 检查当前位置是否有蛇或梯子，如果有则跳转到目标位置
                if (getValue(board, cur) != -1) {
                    cur = getValue(board, cur);
                }

                // 到达终点，返回当前层数（最少移动次数）
                if (cur == nums) {
                    return level;
                }

                // 如果当前位置未访问过，进行扩展
                if (!visited.contains(cur)) {
                    visited.add(cur);  // 标记为已访问

                    // 投掷骰子1-6，将所有可能到达的下一位置加入队列
                    for (int j = cur + 1; j <= Math.min(cur + 6, nums); j++) {
                        queue.offer(j);
                    }
                }
            }

            level++;  // 完成一层探索，移动次数加1
        }

        // 队列空了还没到达终点，说明无法到达
        return -1;
    }


    /**
     * 蛇与梯子游戏 - 找到从起点(1)到终点(n²)的最少移动次数
     * <p>
     * 算法思路：使用深度优先搜索(DFS) + 记忆化缓存优化
     * ⚠️ 注意：此实现在某些复杂情况下可能返回错误结果，建议使用BFS算法
     *
     * @param board 二维棋盘，-1表示普通格子，其他数字表示蛇或梯子的目标位置
     * @return 最少移动次数，如果无法到达则返回-1
     */
    public int snakesAndLadders1(int[][] board) {
        int n = board.length;           // 棋盘行数
        int m = board[0].length;        // 棋盘列数（正方形棋盘，n == m）
        int nums = n * m;              // 总格子数

        int[] flag = new int[nums + 1];     // 标记当前路径中已访问的格子，避免循环
        int[] memo = new int[nums + 1];     // 记忆化缓存，记录从起点到每个位置的最少步数

        // 初始化记忆化缓存，-1表示该位置尚未计算过
        for (int i = 1; i <= nums; i++) {
            memo[i] = -1;
        }

        // 从位置1开始，初始移动次数为0
        int result = dfs(board, flag, memo, 1, 0);

        // 如果结果超过格子总数，说明无法到达终点，返回-1
        return result > nums ? -1 : result;
    }

    /**
     * 深度优先搜索 + 记忆化缓存
     *
     * @param board 棋盘数组
     * @param flag  当前路径访问标记数组
     * @param memo  记忆化缓存数组
     * @param i     当前位置
     * @param times 到达当前位置的移动次数
     * @return 从当前位置到终点的最少总移动次数
     */
    private int dfs(int[][] board, int[] flag, int[] memo, int i, int times) {
        int nums = board.length * board[0].length;

        // 到达终点，返回当前移动次数
        if (i == nums) {
            return times;
        }

        // 记忆化缓存剪枝：如果之前已经用更少的步数到达过位置i，则剪枝
        if (memo[i] != -1 && memo[i] <= times) {
            return Integer.MAX_VALUE; // 返回极大值，表示这条路径不是最优的
        }

        // 更新记忆化缓存：记录到达位置i的最少步数
        if (memo[i] == -1 || times < memo[i]) {
            memo[i] = times;
        }

        int ans = Integer.MAX_VALUE;

        // 投掷骰子：从当前位置i开始，可以移动1-6步
        // i+1 对应投掷1点，i+2 对应投掷2点，...，i+6 对应投掷6点
        for (int j = i + 1; j <= Math.min(i + 6, nums); j++) {
            // 如果格子j已在当前路径中访问过，跳过（避免形成循环）
            if (flag[j] == 1) {
                continue;
            }

            // 标记格子j为已访问（进入当前路径）
            flag[j] = 1;

            // 获取格子j的值：-1表示普通格子，其他数字表示蛇或梯子的目标位置
            int v = getValue(board, j);
            int nextPos = (v != -1) ? v : j; // 如果有蛇/梯子，跳转到目标位置；否则停在原位

            // 递归搜索从下一个位置出发的最少步数
            int result = dfs(board, flag, memo, nextPos, times + 1);
            ans = Math.min(ans, result); // 取所有可能路径中的最小值

            // 回溯：取消标记格子j（离开当前路径）
            flag[j] = 0;
        }

        return ans;
    }

    /**
     * 根据一维坐标获取棋盘上的值
     * 蛇与梯子棋盘的编号规则：从左下角开始，蛇形向上编号
     *
     * @param board 棋盘数组
     * @param i     一维坐标（从1开始）
     * @return 棋盘格子的值（-1表示普通格子，其他数字表示蛇/梯子目标）
     */
    private int getValue(int[][] board, int i) {
        int n = board.length;    // 棋盘大小
        int m = board[0].length; // 列数（等于n）

        // 计算二维坐标
        int row = (i - 1) / m;   // 行号（从下往上）
        int col = (i - 1) % m;   // 列号

        // 蛇形编号：偶数行从左到右，奇数行从右到左
        if (row % 2 == 0) {
            // 偶数行：从左到右
            return board[n - 1 - row][col];
        } else {
            // 奇数行：从右到左
            return board[n - 1 - row][m - 1 - col];
        }
    }

    /**
     * 单元测试方法
     * 测试蛇与梯子算法在各种棋盘配置下的正确性
     *
     * 测试用例说明：
     * 1. 3x3棋盘包含多个蛇和梯子，验证基本功能
     * 2. 7x7复杂棋盘，测试算法在复杂情况下的表现
     * 3. 2x2简单棋盘，测试无蛇梯情况下的基础移动
     * 4. 1x1单格棋盘，测试边界条件
     */
    @Test
    public void test() {
        Snakes_And_Ladders_909 solution = new Snakes_And_Ladders_909();

        // 测试用例1: 3x3棋盘，包含蛇和梯子的基本测试
        // 棋盘布局：
        // -1  4 -1  (第1行：蛇到位置3，梯子到位置14)
        //  6  2  6  (第2行：梯子到位置6，普通格2，梯子到位置6)
        // -1  3 -1  (第3行：普通格，蛇到位置15，普通格)
        // 预期最少步数：2
        int[][] board1 = {{-1, 4, -1}, {6, 2, 6}, {-1, 3, -1}};
        assertEquals(2, solution.snakesAndLadders(board1));

        // 测试用例2: 7x7复杂棋盘，测试多个蛇梯组合的最优路径
        // 包含多个复杂的蛇梯交互，考验算法的路径搜索能力
        // 预期最少步数：4
        int[][] board2 = {
                {-1, -1, 27, 13, -1, 25, -1},    // 第1行：梯子到27，梯子到13，梯子到25
                {-1, -1, -1, -1, -1, -1, -1},    // 第2行：全部普通格
                {44, -1, 8, -1, -1, 2, -1},      // 第3行：蛇到44，梯子到8，梯子到2
                {-1, 30, -1, -1, -1, -1, -1},    // 第4行：梯子到30
                {3, -1, 20, -1, 46, 6, -1},      // 第5行：梯子到3，梯子到20，梯子到46，梯子到6
                {-1, -1, -1, -1, -1, -1, 29},    // 第6行：梯子到29
                {-1, 29, 21, 33, -1, -1, -1}      // 第7行：蛇到29，梯子到21，梯子到33
        };
        assertEquals(4, solution.snakesAndLadders(board2));

        // 测试用例3: 2x2简单棋盘，无蛇梯情况
        // 需要从位置1移动到位置4，最少需要投掷骰子3次（1→2→3→4）
        // 但实际上可以直接从1投掷3点到4，所以只需要1步
        int[][] board3 = {{-1, -1}, {-1, -1}};
        assertEquals(1, solution.snakesAndLadders(board3));

        // 测试用例4: 1x1单格棋盘，边界条件测试
        // 已经在终点位置，不需要移动
        int[][] board4 = {{-1}};
        assertEquals(0, solution.snakesAndLadders(board4));
    }
}
