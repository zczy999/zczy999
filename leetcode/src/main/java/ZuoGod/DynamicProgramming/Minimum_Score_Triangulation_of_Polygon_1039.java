package ZuoGod.DynamicProgramming;

public class Minimum_Score_Triangulation_of_Polygon_1039 {

    public int minScoreTriangulation(int[] values) {

        int[][] cache = new int[values.length][values.length];
        int min = f1(0, values.length - 1, values, cache);
        return min;
    }

    private int f(int l, int r, int[] values) {
        if (l == r || l == r - 1) {
            return 0;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = l + 1; i < r; i++) {
            ans = Math.min(ans, f(l, i, values) + f(i, r, values) + values[l] * values[i] * values[r]);

        }

        return ans;
    }

    private int f1(int l, int r, int[] values, int[][] cache) {

        if (cache[l][r] != 0) {
            return cache[l][r];
        }

        if (l == r || l == r - 1) {
            return 0;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = l + 1; i < r; i++) {
            ans = Math.min(ans, f1(l, i, values, cache) + f1(i, r, values, cache) + values[l] * values[i] * values[r]);

        }
        cache[l][r] = ans;
        return ans;
    }

}
