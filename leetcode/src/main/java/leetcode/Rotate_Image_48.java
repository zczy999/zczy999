package leetcode;

public class Rotate_Image_48 {


    /**
     * 旋转 n x n 二维矩阵 90 度（顺时针）- 按层旋转法
     * 算法思路：从外到内逐层旋转，每层旋转四个边的对应元素
     *
     * 时间复杂度：O(n²) - 需要处理矩阵中的每个元素
     * 空间复杂度：O(1) - 原地旋转，只使用常数额外空间
     *
     * 算法步骤：
     * 1. 从外层到内层逐层处理（layer = 0, 1, 2, ...）
     * 2. 对每一层的每个元素，进行四边旋转：上→右→下→左→上
     * 3. 使用临时变量保存一个位置，然后依次移动其他三个位置
     *
     * 示例（3x3矩阵的第一层旋转）：
     * 原始：                    旋转后：
     * [1,2,3]                  [7,4,1]
     * [4,5,6]   => 旋转外围 =>  [8,5,2]
     * [7,8,9]                  [9,6,3]
     *
     * @param matrix n x n 的二维矩阵，必须在原矩阵上修改
     */
    public void rotate(int[][] matrix) {
        // 外层循环：逐层处理矩阵
        // matrix.length / 2 表示需要处理的层数
        // 例如：4x4矩阵需要处理2层，5x5矩阵需要处理2层（中间元素不需要移动）
        for (int layer = 0; layer < matrix.length / 2; layer++){
            // 计算当前层的底部行号
            // 对于第0层，bottom = n-1；对于第1层，bottom = n-2，以此类推
            int bottom = matrix.length - 1 - layer;

            // 内层循环：处理当前层的每个元素（除了最后一个，避免重复处理）
            // j 从 layer 开始，到 matrix.length - layer - 1 结束
            // 这样确保只处理当前层的有效元素范围
            for (int j = layer; j < matrix.length - layer - 1; j++){
                // 计算当前元素在当前层中的偏移量
                // 用于计算四个对应位置的坐标
                int offset = j - layer;

                // 第1步：保存上边元素（将被右边元素替换）
                // 位置：[layer][j] - 当前层的上边
                int top = matrix[layer][j];

                // 第2步：左边元素移动到上边位置
                // 位置：[bottom-offset][layer] → [layer][j]
                // bottom-offset 计算左边对应元素的行号
                matrix[layer][j] = matrix[bottom-offset][layer];

                // 第3步：下边元素移动到左边位置
                // 位置：[bottom][bottom-offset] → [bottom-offset][layer]
                // bottom-offset 计算下边对应元素的列号
                matrix[bottom-offset][layer] = matrix[bottom][bottom-offset];

                // 第4步：右边元素移动到下边位置
                // 位置：[j][bottom] → [bottom][bottom-offset]
                // j 保持不变，因为右边的行号与上边的列号对应
                matrix[bottom][bottom-offset] = matrix[j][bottom];

                // 第5步：上边元素（已保存）移动到右边位置
                // 位置：保存的 top → [j][bottom]
                // 完成四边旋转的闭环
                matrix[j][bottom] = top;
            }
        }
    }


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
    public void rotate1(int[][] matrix) {
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
