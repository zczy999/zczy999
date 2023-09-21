package leetcode;

public class Candy_135 {
    public static void main(String[] args) {
        int[] ratings = {1,0,2};
        int candy = Candy_135.candy(ratings);
        System.out.println(candy);
    }

    public static int candy(int[] ratings) {
        int uplen = 1, dl = 0;
        int res = 1;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                if (dl != 0) {
                    uplen = 1;
                }
                dl = 0;
                uplen++;
                res += uplen;
            } else if (ratings[i] == ratings[i - 1]) {
                uplen = 0;
                dl = 1;
                res += dl;
            } else {
                dl++;
                if (uplen == dl) {
                    dl++;
                }
                res += dl;
            }

        }
        return res;
    }


    /**
     * 自己写的解有几个问题
     * 1.res[] 不应该把这个当成输出，增加了问题的复杂性
     * 2.对于ratings[i] == ratings[i - 1]情况没有分开处理
     * 3.对于ul，dl何时初始化没有想清楚
     *
     * @param ratings
     * @return
     */
    public int candy1(int[] ratings) {
        int ul = 0;
        int dl = 0;
        int[] res = new int[ratings.length];
        res[0] = 1;

        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                if (dl == 1) {
                    res[i - 1] = 1;
                }
                res[i] = res[i - 1] + 1;
                ul++;
                continue;
            }
            if (res[i - 1] == 1) {
                res[i] = 1;
            } else {
                res[i] = res[i - 1] - 1;
            }

            dl++;


        }
        return 0;
    }
}
