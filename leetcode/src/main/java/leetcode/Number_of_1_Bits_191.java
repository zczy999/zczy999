package leetcode;

public class Number_of_1_Bits_191 {

    /**
     * 这是对于二进制的一个计数方法
     * @param n 输入的整数
     * @return 二进制表示中1的个数
     */
    public int hammingWeight(int n) {
        int res = 0;
        while (n != 0) {
            res += n % 2;
            n /= 2;
        }
        return res;
    }

    /**
     * 计算 n 在 b 进制下，数字 '1' 出现了多少次
     * @param n 输入的整数
     * @param b 进制
     */
    public int countOnesInBase(int n, int b) {
        int res = 0;
        while (n != 0) {
            // 取出 n 在 b 进制下的最低位数字 r ∈ [0, b-1]
            int r = n % b;
            if (r == 1) {
                res++;
            }
            n /= b;    // 相当于把 n 右移一位（b 进制意义下）
        }
        return res;
    }

    /**
     * 找出 n 在所有进制下，数字 '1' 出现的最多次数
     * @param n 输入的整数
     * @return 1 出现的最多次数
     */
    public int maxOnesAcrossBases(int n) {
        int maxCount = 0;
        int bestBase = 2;
        for (int b = 2; b <= 36; b++) {
            int cnt = countOnesInBase(n, b);
            if (cnt > maxCount) {
                maxCount = cnt;
                bestBase = b;
            }
        }
        System.out.printf("对 n=%d，最佳进制是 %d，1 的个数 = %d%n", n, bestBase, maxCount);
        return maxCount;
    }


}
