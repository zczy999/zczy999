package leetcode;

public class Rotate_Image_48 {


    /**
     * 旋转 n x n 二维矩阵 90 度（顺时针）
     * 算法思路：先转置矩阵，然后翻转每一行
     *
     * 原始矩阵：
     * [1,2,3]     转置后：        翻转后（最终结果）：
     * [4,5,6]  => [1,4,7]   =>   [7,4,1]
     * [7,8,9]     [2,5,8]   =>   [8,5,2]
     *              [3,6,9]   =>   [9,6,3]
     *
     * @param matrix n x n 的二维矩阵，必须在原矩阵上修改
     */
    public void rotate(int[][] matrix) {
        // 第一步：矩阵转置（沿主对角线翻转）
        transpose(matrix);
        // 第二步：水平翻转每一行
        flip(matrix);
    }

    /**
     * 水平翻转矩阵的每一行
     * 对于每一行，将左右对称的元素进行交换
     *
     * 示例（单行）：
     * 输入：[7,4,1]  j=0时交换7和1，j=1时交换4和4（自己和自己）
     * 输出：[1,4,7]
     *
     * @param matrix n x n 的二维矩阵
     */
    private void flip(int[][] matrix) {
        // 遍历每一行
        for (int i = 0; i < matrix.length; i++) {
            // 只需要遍历到列的中点，避免重复交换
            // matrix[0].length / 2 确保每对元素只交换一次
            for (int j = 0; j < matrix[0].length / 2; j++) {
                // 使用临时变量交换左右对称的两个元素
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][matrix.length - 1 - j];
                matrix[i][matrix.length - 1 - j] = temp;
            }
        }
    }

    /**
     * 矩阵转置（沿主对角线翻转）
     * 将 matrix[i][j] 与 matrix[j][i] 交换
     *
     * 示例：
     * 输入矩阵：        转置后矩阵：
     * [1,2,3]          [1,4,7]
     * [4,5,6]    =>    [2,5,8]
     * [7,8,9]          [3,6,9]
     *
     * @param matrix n x n 的二维矩阵
     */
    private void transpose(int[][] matrix) {
        // 遍历矩阵的上三角部分（包括主对角线）
        // i 从 0 到 n-1，j 从 i 到 n-1
        // 这样确保每对 (i,j) 和 (j,i) 只交换一次
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i; j < matrix[0].length; j++) {
                // 交换主对角线两侧的对称元素
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

    }

}
