package leetcode;

public class Factorial_Trailing_Zeroes_172 {

    /**
     * 计算阶乘n!中末尾零的数量
     * 末尾零的数量由阶乘中因子10的数量决定，而因子10由因子2和5组成
     * 由于因子2的数量总是多于因子5，因此只需计算因子5的数量即可
     *
     * @param n 阶乘的数，即需要计算n!中末尾零的数量
     * @return 返回n!中末尾零的数量
     */
    public int trailingZeroes(int n) {
        // 初始化末尾零的数量为0
        int ans = 0;
        // 从5开始，以5为步长遍历到n，因为每5个数会有一个额外的因子5
        for (int i = 5; i <= n; i += 5) {
            // 对每个是5的倍数的数i，计算其所有因子5的贡献
            for (int x = i; x % 5 == 0; x /= 5) {
                // 每次x除以5，计数器ans加一，直到x不再是5的倍数
                ++ans;
            }
        }
        // 返回累计的因子5的数量，即末尾零的数量
        return ans;
    }

}
