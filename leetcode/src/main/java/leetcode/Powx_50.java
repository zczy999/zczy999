package leetcode;

public class Powx_50 {

    public double myPow(double x, int n) {
        long N = n;
        return n >= 0 ? quickPow(x, N) : 1.0 / quickPow(x, -N);
    }

    public double quickPow(double x, long n) {
        if (n == 0) {
            return 1;
        }
        double y = quickPow(x, n / 2);
        y = n % 2 == 0 ? y * y : y * y * x;
        return y;


    }

}
