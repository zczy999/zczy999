package leetcode;

import java.util.*;

public class IPO_502 {
    public static void main(String[] args) {
        IPO_502 res = new IPO_502();
        int[] profits = {1, 2, 3};
        int[] capital = {0, 1, 1};
        int maximizedCapital = res.findMaximizedCapital(2, 3, profits, capital);
        System.out.println(maximizedCapital);


    }

    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(new int[]{capital[i], profits[i]});
        }
        Collections.sort(list, (a, b) -> a[0] - b[0]);
        PriorityQueue<Integer> q = new PriorityQueue<>((a, b) -> b - a);
        int i = 0;
        while (k-- > 0) {
            while (i < n && list.get(i)[0] <= w) q.add(list.get(i++)[1]);
            if (q.isEmpty()) break;
            w += q.poll();
        }
        return w;
    }


    /**
     * 超时 o(KN)
     * 要以空间换时间基本思路
     *
     * @param k
     * @param w
     * @param profits
     * @param capital
     * @return
     */
    public int findMaximizedCapital1(int k, int w, int[] profits, int[] capital) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(((o1, o2) -> o2 - o1));
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < capital.length; j++) {
                int c = capital[j];
                if (c <= w) {
                    if (set.add(j)) {
                        pq.offer(profits[j]);
                    }
                }
            }
            if (!pq.isEmpty()) {
                w += pq.poll();
            }
        }
        return w;

    }
}
