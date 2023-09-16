package leetcode;

public class Gas_Station_134 {

    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int cur;
        for (int i = 0; i < gas.length; i++) {
            if (gas[i] < cost[i]) {
                continue;
            }
            int gasSum = 0;
            int gasCostSum = 0;
            for (int j = i; j < i + gas.length; j++) {
                int n = j % gas.length;
                gasSum = gasSum + gas[n];
                gasCostSum = gasCostSum + cost[n];
                if (gasSum < gasCostSum) {
                    //i>=gas.length即代表到了i之前的点，而i之前的点已经失败了才能到这，即说明没有点能完成循环
                    i = j;
                    break;
                }
                if (j == i + gas.length - 1) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] gas = {2, 3, 4};
        int[] cost = {3, 4, 3};
        int i = Gas_Station_134.canCompleteCircuit(gas, cost);
        System.out.println(i);
    }
}
