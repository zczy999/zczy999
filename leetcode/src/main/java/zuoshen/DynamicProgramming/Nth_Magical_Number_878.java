package zuoshen.DynamicProgramming;

public class Nth_Magical_Number_878 {
    static final int MOD = 1000000007;

    public static void main(String[] args) {
        Nth_Magical_Number_878 res = new Nth_Magical_Number_878();
        int i = res.nthMagicalNumber(1000000000, 39999, 40000);
        System.out.println(i);
    }

    public int nthMagicalNumber(int n, int a, int b) {
        int ab = getAB(a, b);
        long left = 0;
        long right = (long) n * Math.min(a, b);
        while (left + 1 < right) {
            long mid = (left + right) / 2;
            if ((mid / a + mid / b - mid / ab) < n) {
                left = mid;
            } else {
                right = mid;
            }
        }
        if ((left / a + left / b - left / ab) == n) {
            return (int) (left % MOD);
        }
        return (int)(right % MOD);

    }


    private int getAB(int a, int b) {
        int ab = getab(a, b);
        return (a / ab) * b;
    }

    private int getab(int a, int b) {
        return b == 0 ? a : getab(b, a % b);
    }

    public int nthMagicalNumber1(int n, int a, int b) {
        long l = Math.min(a, b);
        long r = (long) n * Math.min(a, b);
        int c = lcm(a, b);
        while (l <= r) {
            long mid = (l + r) / 2;
            long cnt = mid / a + mid / b - mid / c;
            if (cnt >= n) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        int t = (int) ((l) % MOD);
        return (int) ((r + 1) % MOD);
    }

    public int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    public int gcd(int a, int b) {
        return b != 0 ? gcd(b, a % b) : a;
    }


}
