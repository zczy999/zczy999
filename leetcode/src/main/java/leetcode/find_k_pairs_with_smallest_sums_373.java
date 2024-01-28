package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class find_k_pairs_with_smallest_sums_373 {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> res = new ArrayList<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o2[1] + o2[0] - o1[1] - o1[0]);
        int len = 0;

        for (int i : nums1) {
            for (int j : nums2) {
                if (len < k) {
                    int[] cur = new int[]{i, j};
                    pq.offer(cur);
                    len++;
                    continue;
                }

                int[] cur = pq.peek();
                if ((cur[0] + cur[1]) > (i + j)) {
                    pq.poll();
                    int[] cu = new int[]{i, j};
                    pq.offer(cu);
                } else {
                    break;
                }

            }
        }

        while (!pq.isEmpty()) {
            int[] tem = pq.poll();
            List<Integer> list = new ArrayList<>();
            list.add(tem[0]);
            list.add(tem[1]);
            res.add(list);
        }
        return res;
    }
}
