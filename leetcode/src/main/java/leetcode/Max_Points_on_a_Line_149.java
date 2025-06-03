package leetcode;

import java.util.HashMap;
import java.util.Map;

public class Max_Points_on_a_Line_149 {

    public static final int LEN = 20000;

    /**
     * 计算在平面上给定的点集中，处于同一直线上的最大点数
     *
     * @param points 二维数组，每个元素表示平面上的一个点，第一个元素是x坐标，第二个元素是y坐标
     * @return 返回处于同一直线上的最大点数
     */
    public int maxPoints(int[][] points) {
        // 如果点集为空，则直接返回0
        if (points.length == 0) {
            return 0;
        }
        // 初始化结果为1，至少存在一个点
        int res = 1;
        // 遍历每个点，作为可能的直线上的起点
        for (int i = 0; i < points.length; i++) {
            // 使用哈希表记录每条直线上的点数
            Map<Integer, Integer> map = new HashMap<>();
            // 遍历当前点之后的所有点，作为可能的直线上的其他点
            for (int j = i + 1; j < points.length; j++) {
                // 计算当前两点之间的横纵坐标差
                int dx = points[j][0] - points[i][0];
                int dy = points[j][1] - points[i][1];
                // 如果横坐标差为0，设置纵坐标差为1，避免除以0的情况
                if (dx == 0) {
                    dy = 1;
                }
                // 如果纵坐标差为0，设置横坐标差为1，避免除以0的情况
                if (dy == 0) {
                    dx = 1;
                }
                // 如果横坐标差为负，将横纵坐标差均取反，保证斜率正向
                if (dx < 0) {
                    dx = -dx;
                    dy = -dy;
                }
                // 计算横纵坐标差的最大公约数，用于简化斜率
                int gcd = getGCD(Math.abs(dx), Math.abs(dy));
                // 构造斜率的唯一表示，用于哈希表的键
                int key = (dx / gcd) * LEN + (dy / gcd);
                // 更新当前斜率对应的点数
                map.put(key, map.getOrDefault(key, 0) + 1);
                // 更新最大点数
                res = Math.max(res, map.get(key) + 1);
            }
        }
        // 返回最大点数
        return res;
    }


    private int getGCD(int x, int y) {
        if (y > x) {
            return getGCD(y, x);
        }
        if (y == 0) {
            return x;
        }
        return getGCD(y, x % y);
    }

}
