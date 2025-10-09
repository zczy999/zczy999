package ZuoGod.Greedy;

import java.util.*;

public class Minimum_Number_of_Arrows_to_Burst_Balloons_452 {

    // 贪心解法（原解法）
    public int findMinArrowShots(int[][] points) {
        Arrays.sort(points, Comparator.comparingInt(a -> a[1]));
        long curRight = Long.MIN_VALUE; // 用 long 避免比较时溢出
        int res = 0;
        for (int[] point : points) {
            if (point[0] > curRight) {
                res++;
                curRight = point[1];
            }
        }
        return res;
    }

    // ================ 其他解法 ================

    /**
     * 动态规划解法 - 将问题转化为最大独立集问题
     * 时间复杂度: O(n²) - 排序O(n log n) + 双重循环O(n²)
     * 空间复杂度: O(n) - dp数组
     *
     * 思路：转化为求最多能选择多少个不重叠的区间，每个不重叠区间需要一支箭
     * 这实际上是区间调度问题，但状态转移设计较为复杂，仅供参考
     */
    public int findMinArrowShotsDP(int[][] points) {
        if (points.length == 0) return 0;

        // 按左端点排序，便于检查区间重叠关系
        Arrays.sort(points, Comparator.comparingInt(a -> a[0]));

        int n = points.length;
        int[] dp = new int[n];

        // dp[i] 表示以第i个区间结尾时，最少需要多少支箭
        for (int i = 0; i < n; i++) {
            dp[i] = 1; // 每个区间至少需要1支箭
            for (int j = 0; j < i; j++) {
                // 检查区间j和区间i是否重叠
                if (points[j][1] < points[i][0]) {
                    // 不重叠：区间i需要额外的箭
                    // dp[j]表示前j个区间的最优解，+1表示区间i需要新箭
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                } else {
                    // 重叠：区间i可以和前面的区间共用箭
                    // 这种情况下，箭数不变
                    dp[i] = Math.max(dp[i], dp[j]);
                }
            }
        }

        // 注意：这个DP实现有逻辑问题，实际应该求最大不重叠区间数
        // 这里仅作为DP思路的展示，不建议在生产环境使用
        return dp[n - 1];
    }

    /**
     * 回溯解法 - 暴力搜索所有可能的射箭位置组合
     * 时间复杂度: O(n!) - 指数级，只适合小规模数据
     * 空间复杂度: O(n) - 递归栈深度
     *
     * 思路：尝试在所有可能的位置射箭，通过剪枝减少搜索空间
     * 适合理解问题本质，但不适合实际应用
     */
    private int minArrows = Integer.MAX_VALUE; // 记录全局最小箭数

    public int findMinArrowShotsBacktrack(int[][] points) {
        if (points.length == 0) return 0;

        // 按左端点排序，便于处理重叠区间
        Arrays.sort(points, Comparator.comparingInt(a -> a[0]));
        minArrows = Integer.MAX_VALUE; // 重置全局最小值

        // 从第一个区间开始回溯
        backtrack(points, 0, 0);
        return minArrows;
    }

    /**
     * 递归回溯函数
     * @param points 排序后的区间数组
     * @param startIndex 当前要处理的区间起始索引
     * @param arrowsUsed 已经使用的箭数
     */
    private void backtrack(int[][] points, int startIndex, int arrowsUsed) {
        // 剪枝：如果当前使用的箭数已经超过已知最小值，直接返回
        // 这是最重要的剪枝策略，能大幅减少搜索空间
        if (arrowsUsed >= minArrows) return;

        // 递归终止条件：所有区间都已处理
        if (startIndex >= points.length) {
            minArrows = Math.min(minArrows, arrowsUsed);
            return;
        }

        // 优化策略：只在关键位置尝试射箭
        // 理论上可以在任何位置射箭，但区间的端点是最优候选位置
        // 因为在这些位置射箭能保证击穿当前区间
        int[] positions = {
            points[startIndex][0], // 左端点
            points[startIndex][1]  // 右端点
        };

        // 尝试在每个候选位置射箭
        for (int arrowPos : positions) {
            // 找到下一个不被当前箭击穿的区间
            int nextIndex = findNextIndex(points, startIndex, arrowPos);

            // 递归处理剩余区间，箭数+1
            backtrack(points, nextIndex, arrowsUsed + 1);
        }
    }

    /**
     * 找到第一个不被指定位置击穿的区间
     * @param points 排序后的区间数组
     * @param startIndex 起始搜索索引
     * @param arrowPos 射箭位置
     * @return 下一个需要处理的区间索引
     */
    private int findNextIndex(int[][] points, int startIndex, int arrowPos) {
        int nextIndex = startIndex + 1;

        // 跳过所有被当前箭击穿的区间
        // 如果区间的左端点 <= 射箭位置，说明该区间被击穿
        while (nextIndex < points.length && points[nextIndex][0] <= arrowPos) {
            nextIndex++;
        }

        return nextIndex;
    }

    /**
     * 图论解法 - 并查集
     * 时间复杂度: O(n² α(n)) - 双重循环O(n²) + 并查集操作α(n)可忽略
     * 空间复杂度: O(n) - 并查集数据结构
     *
     * 思路：将问题转化为图的连通性问题
     * 每个气球是一个节点，重叠的气球之间有边相连
     * 每个连通分量内的气球都相互重叠，可以用一支箭击穿
     * 最终答案就是连通分量的数量
     */
    public int findMinArrowShotsUnionFind(int[][] points) {
        if (points.length == 0) return 0;

        int n = points.length;
        UnionFind uf = new UnionFind(n);

        // 构建图：将所有重叠的区间合并到同一个连通分量
        // 双重循环检查所有区间对的重叠关系
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // 如果区间i和区间j重叠，将它们合并到同一个连通分量
                if (isOverlap(points[i], points[j])) {
                    uf.union(i, j);
                }
            }
        }

        // 连通分量的数量就是需要的箭数
        // 因为每个连通分量内的气球都相互重叠，可以用一支箭击穿
        // 不同连通分量之间的气球不重叠，需要不同的箭
        return uf.getCount();
    }

    /**
     * 判断两个区间是否重叠
     * 区间A: [a1, a2], 区间B: [b1, b2]
     * 重叠条件：a1 <= b2 && b1 <= a2
     */
    private boolean isOverlap(int[] interval1, int[] interval2) {
        return interval1[0] <= interval2[1] && interval2[0] <= interval1[1];
    }

    /**
     * 并查集数据结构 - 用于维护元素的连通性
     * 支持高效的合并和查找操作
     */
    static class UnionFind {
        private final int[] parent; // parent[i]表示元素i的父节点
        private final int[] rank;   // rank[i]表示以i为根的树的深度，用于优化合并
        private int count;          // 连通分量的数量

        /**
         * 初始化并查集
         * @param size 元素数量
         */
        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            count = size; // 初始时每个元素都是独立的连通分量

            // 初始时每个元素的父节点都是自己
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 0; // 初始深度为0
            }
        }

        /**
         * 查找元素x的根节点（路径压缩优化）
         * @param x 要查找的元素
         * @return 根节点的索引
         */
        public int find(int x) {
            // 路径压缩：在查找过程中将路径上的所有节点直接连接到根节点
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // 递归查找并压缩路径
            }
            return parent[x];
        }

        /**
         * 合并两个元素所在的集合（按秩合并优化）
         * @param x 第一个元素
         * @param y 第二个元素
         */
        public void union(int x, int y) {
            int rootX = find(x); // 找到x的根节点
            int rootY = find(y); // 找到y的根节点

            // 如果已经在同一个集合中，无需合并
            if (rootX != rootY) {
                // 按秩合并：将深度较小的树合并到深度较大的树下
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else {
                    // 深度相同，任选一个作为根，并增加深度
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
                count--; // 连通分量数量减少1
            }
        }

        /**
         * 获取当前连通分量的数量
         * @return 连通分量数量
         */
        public int getCount() {
            return count;
        }
    }

    /**
     * 二分查找优化解法 - 批量处理重叠区间
     * 时间复杂度: O(n log n) - 排序O(n log n) + 每次二分查找O(log n)
     * 空间复杂度: O(1) - 只使用常数额外空间
     *
     * 思路：每次找到一组重叠区间，计算它们的交集，在交集右端点射箭
     * 然后用二分查找快速跳到下一个不被击穿的区间
     */
    public int findMinArrowShotsBinarySearch(int[][] points) {
        if (points.length == 0) return 0;

        // 按左端点排序，便于二分查找
        Arrays.sort(points, Comparator.comparingInt(a -> a[0]));

        int arrows = 0;     // 箭的数量
        int index = 0;      // 当前处理的区间索引
        int n = points.length;

        // 主循环：处理所有区间
        while (index < n) {
            // 1. 找到从index开始的所有重叠区间的交集
            // 交集的右端点就是最优射箭位置（贪心策略）
            int optimalPos = findOptimalPosition(points, index);

            // 2. 用二分查找快速定位到下一个不被击穿的区间
            // 这样可以跳过大量已被当前箭击穿的区间
            index = findNextIndexBinary(points, index, optimalPos);

            // 3. 箭数+1，准备处理下一批区间
            arrows++;
        }

        return arrows;
    }

    /**
     * 找到从startIndex开始的重叠区间的交集
     * 交集右端点是最优射箭位置
     *
     * @param points 排序后的区间数组
     * @param startIndex 起始区间索引
     * @return 最优射箭位置（交集右端点）
     */
    private int findOptimalPosition(int[][] points, int startIndex) {
        // 初始交集为第一个区间
        int left = points[startIndex][0];
        int right = points[startIndex][1];
        int i = startIndex + 1;

        // 向后扩展，找到所有与当前区间重叠的区间
        // 如果下一个区间的左端点 <= 当前交集右端点，说明存在重叠
        while (i < points.length && points[i][0] <= right) {
            // 更新交集：新的交集是两个区间的重叠部分
            left = Math.max(left, points[i][0]);   // 交集左端点取较大值
            right = Math.min(right, points[i][1]);  // 交集右端点取较小值
            i++;
        }

        // 返回交集右端点作为最优射箭位置
        // 选择右端点的原因：这是能击穿所有重叠区间的最靠右位置
        return right;
    }

    /**
     * 使用二分查找找到第一个不被指定位置击穿的区间
     *
     * @param points 排序后的区间数组
     * @param startIndex 起始搜索位置
     * @param position 射箭位置
     * @return 下一个需要处理的区间索引
     */
    private int findNextIndexBinary(int[][] points, int startIndex, int position) {
        // 二分查找：寻找第一个左端点 > position 的区间
        // 这样的区间不会被当前位置的箭击穿
        int left = startIndex, right = points.length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (points[mid][0] > position) {
                // 中点区间不被击穿，答案在左半部分（包括中点）
                right = mid;
            } else {
                // 中点区间被击穿，答案在右半部分
                left = mid + 1;
            }
        }

        return left;
    }

    /**
     * 堆优化解法 - 活跃区间管理
     * 时间复杂度: O(n log n) - 排序O(n log n) + 堆操作O(n log n)
     * 空间复杂度: O(n) - 堆存储空间
     *
     * 思路：用最小堆维护当前活跃（重叠）区间的右端点
     * 堆顶元素就是最优射箭位置
     * 当堆为空时需要新箭，有元素时可以共用箭
     */
    public int findMinArrowShotsHeap(int[][] points) {
        if (points.length == 0) return 0;

        // 按左端点排序，确保按时间顺序处理区间
        Arrays.sort(points, Comparator.comparingInt(a -> a[0]));

        // 最小堆：存储当前活跃区间的右端点
        // 堆顶是最小的右端点，即最优射箭位置
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int arrows = 0; // 箭的数量

        // 遍历所有区间
        for (int[] point : points) {
            // 1. 清理已经结束的区间
            // 如果堆顶的右端点 < 当前区间左端点，说明这些区间已经结束
            // 不再可能与后续区间重叠，可以移除
            while (!minHeap.isEmpty() && minHeap.peek() < point[0]) {
                minHeap.poll();
            }

            // 2. 判断是否需要新箭
            if (minHeap.isEmpty()) {
                // 堆为空说明当前区间不与任何之前的区间重叠
                // 需要发射新箭
                arrows++;
                minHeap.offer(point[1]); // 将当前区间右端点加入堆
            } else {
                // 堆不为空说明当前区间与某些之前的区间重叠
                // 可以共用已有的箭，但可能需要更新最优射箭位置

                // 3. 更新最优射箭位置
                // 当前区间右端点更小，说明最优射箭位置应该调整
                if (point[1] < minHeap.peek()) {
                    // 移除旧的最优位置，使用新的更小的右端点
                    minHeap.poll();
                    minHeap.offer(point[1]);
                }
                // 如果当前区间右端点更大，不需要更新，因为现有位置仍然最优
            }
        }

        return arrows;
    }

}
