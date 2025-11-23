package leetcode;

/**
 * 134. 加油站
 *
 * 题目描述：
 * 在一条环路上有 n 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
 * 你有一辆油箱容量无限的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。
 * 你从其中的一个加油站出发，开始时油箱为空。
 * 给定两个整数数组 gas 和 cost，如果你可以绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1。
 * 如果题目有解，该答案即为唯一答案。
 *
 * 解法：暴力枚举 + 剪枝优化
 * 时间复杂度：O(n²) 最坏情况，但通过剪枝可以优化
 * 空间复杂度：O(1)
 */
public class Gas_Station_134 {

    /**
     * 判断能否完成环路行驶
     *
     * @param gas  每个加油站的汽油量
     * @param cost 从每个加油站到下一站的油耗
     * @return 起始加油站的索引，无解返回 -1
     */
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        // 枚举每个加油站作为起点
        for (int i = 0; i < gas.length; i++) {
            // 剪枝：如果当前站的油量小于到下一站的消耗，直接跳过
            if (gas[i] < cost[i]) {
                continue;
            }

            // 累计加油量和消耗量
            int gasSum = 0;      // 累计获得的汽油
            int gasCostSum = 0;  // 累计消耗的汽油

            // 从 i 站出发，尝试走完一圈（n 个站点）
            for (int j = i; j < i + gas.length; j++) {
                // 使用取模实现环形数组访问
                int n = j % gas.length;
                gasSum = gasSum + gas[n];        // 在当前站加油
                gasCostSum = gasCostSum + cost[n]; // 消耗油量到下一站

                // 如果油量不足以到达下一站，说明从 i 出发失败
                if (gasSum < gasCostSum) {
                    // 关键优化：从 i 到 j 之间的任何站点出发都无法到达 j+1
                    // 因为从 i 出发时油箱是空的，从 i 到 j 之间任意点出发油量只会更少
                    // 所以直接跳过 i 到 j 之间的所有站点，下次从 j+1 开始尝试
                    i = j;
                    break;
                }

                // 成功走完一圈，返回起点索引
                if (j == i + gas.length - 1) {
                    return i;
                }
            }
        }
        // 所有起点都尝试过，无解
        return -1;
    }

    public static void main(String[] args) {
        int[] gas = {2, 3, 4};
        int[] cost = {3, 4, 3};
        int i = Gas_Station_134.canCompleteCircuit(gas, cost);
        System.out.println(i);
    }
}
