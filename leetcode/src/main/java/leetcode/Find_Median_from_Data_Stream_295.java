package leetcode;

import java.util.PriorityQueue;

public class Find_Median_from_Data_Stream_295 {

    private PriorityQueue<Integer> a;
    private PriorityQueue<Integer> b;
    int size;

    public Find_Median_from_Data_Stream_295() {
        a = new PriorityQueue<>((o1, o2) -> o1 - o2);
        b = new PriorityQueue<>((o1, o2) -> o2 - o1);
        size = 0;

    }

    public void addNum(int num) {
        if (size % 2 == 0) {
            a.offer(num);
            Integer poll = a.poll();
            b.offer(poll);
        } else {
            b.offer(num);
            Integer poll = b.poll();
            a.offer(poll);
        }
        size++;
    }

    public double findMedian() {
        if (size == 0) {
            return 0;
        }
        if (size % 2 == 0) {
            double db = b.peek();
            double da = a.peek();
            return (da + db) / 2;
        }
        return b.peek();
    }


/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
}
