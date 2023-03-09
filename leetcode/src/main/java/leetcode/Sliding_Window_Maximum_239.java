package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Sliding_Window_Maximum_239 {

    /**
     * 使用优先队列
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> {
            //小顶堆，return 前减后(o1-o2)； 大顶堆，return 后减前(o2-o1)
            return o2[1] - o1[1];
        });
        int size = 0;
        while (size < k) {
            // 注意这里数组index=0时存储其位置信息，index=1时才是其数值
            pq.add(new int[]{size, nums[size]});
            size++;
        }
        int[] res = new int[nums.length - k + 1];
        res[0] = pq.peek()[1];
        if (nums.length == 1) {
            return res;
        }
        for (int i = k; i < nums.length; i++) {
            while (pq.peek() != null && pq.peek()[0] <= (i - k)) {
                pq.poll();
            }
            pq.add(new int[]{i, nums[i]});
            res[i - k + 1] = pq.peek()[1];
        }
        return res;
    }

    /**
     * 使用双向队列
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow1(int[] nums, int k) {
        Deque<Integer> deque = new ArrayDeque<>();
        // 双向队列两种实现类
        Deque<Integer> deque1 = new LinkedList<>();

        for (int i = 0; i < k; i++) {
            while (deque.peekLast() != null && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            deque.addLast(i);
        }
        int[] res = new int[nums.length - k + 1];
        res[0] = nums[deque.peekFirst()];
        for (int i = k; i < nums.length; i++) {
            while (deque.peekLast() != null && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            deque.addLast(i);
            while (deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            res[i - k + 1] = nums[deque.peekFirst()];

        }

        return res;
    }

}
