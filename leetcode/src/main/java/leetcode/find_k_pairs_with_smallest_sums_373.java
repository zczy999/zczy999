package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class find_k_pairs_with_smallest_sums_373 {

    /**
     * 获取两个数组中所有数对的和最小的k对数
     *
     * @param nums1 第一个数组
     * @param nums2 第二个数组
     * @param k 指定的数对数量
     * @return 返回一个列表，包含所有数对的和最小的k对数
     */
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        // 使用优先队列存储数对索引，以便按和的大小高效地获取下一个数对
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) ->
                nums1[o1[0]] + nums2[o1[1]] - nums1[o2[0]] - nums2[o2[1]]);

        // 初始化结果列表
        List<List<Integer>> res = new ArrayList<>();
        // 将第一个数组的前k个元素（或所有元素，如果不足k个）与第二个数组的第一个元素组成的数对索引加入优先队列
        for (int i = 0; i < Math.min(nums1.length, k); i++) {
            pq.add(new int[]{i, 0});
        }
        // 当优先队列非空且k大于0时，循环执行
        while (!pq.isEmpty() && k > 0) {
            // 从优先队列中获取并移除和最小的数对索引
            int[] cur = pq.poll();
            // 创建一个新的数对列表
            List<Integer> list = new ArrayList<>();
            // 将当前数对的元素添加到列表中
            list.add(nums1[cur[0]]);
            list.add(nums2[cur[1]]);
            // 将当前数对列表添加到结果列表中
            res.add(list);
            // 如果当前数对的第二个元素不是来自第二个数组的最后一个元素，则将下一个数对索引加入优先队列
            if (cur[1] < nums2.length - 1) {
                pq.add(new int[]{cur[0], cur[1] + 1});
            }
            // 更新剩余的数对数量
            k--;
        }
        // 返回结果列表
        return res;
    }


    public List<List<Integer>> kSmallestPairs1(int[] nums1, int[] nums2, int k) {
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
