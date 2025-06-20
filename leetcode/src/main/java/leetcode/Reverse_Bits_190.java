package leetcode;

public class Reverse_Bits_190 {

    /**
     * 反转一个32位无符号整数的二进制位
     * 此方法通过逐位检查和设置来实现位反转
     *
     * @param n 待反转的32位无符号整数
     * @return 反转后的32位无符号整数
     */
    public int reverseBits(int n) {
        // 初始化结果变量为0，用于累积反转后的位
        int res = 0;
        // 遍历32位整数的每一位
        for (int i = 0; i < 32; i++) {
            // 将结果变量左移一位，为下一位的处理腾出空间
            res = res << 1;
            // 检查当前位是否为1，如果是，则在结果中设置相应的位
            if ((n & 1) == 1) {
                res |= 1;
            }
            // 将输入的整数右移一位，准备下一位的检查
            n = n >> 1;
        }
        // 返回累积的反转后的结果
        return res;
    }


    /**
     * 反转给定的32位无符号整数的二进制位
     *
     * @param n 待反转的32位无符号整数
     * @return 反转后的32位无符号整数
     */
    public int reverseBits1(int n) {
        // 初始化结果变量为0，用于构建反转后的整数
        int res = 0;

        // 遍历输入整数的每一位，共32位
        for (int i = 0; i < 32; i++) {
            // 检查当前位（从右到左，i从0开始）是否为1
            if (((n >> i) & 1) == 1) {
                // 如果当前位为1，则在结果的对应位置（从左到右，31-i）设置为1
                res |= 1 << (31 - i);
            }
        }
        // 返回构建完成的反转整数
        return res;
    }


}
