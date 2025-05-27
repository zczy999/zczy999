package leetcode;

import org.junit.Test;

public class Sqrtx_69 {

    @Test
    public void test(){
        int i = mySqrt(4);
        assert i == 2;
    }

    public int mySqrt(int x) {
        int l = 0, r = x;
        int ans = 0;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if ((long) mid * mid <= x) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ans;
    }


}
