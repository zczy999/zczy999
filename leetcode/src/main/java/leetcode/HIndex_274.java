package leetcode;

import java.util.Arrays;

public class HIndex_274 {

    /**
     * 二分答案法 左神做法
     *
     * @param citations
     * @return
     */
    public int hIndex(int[] citations) {
        int left = 0, right = citations.length;
        int res = 0;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (f(mid,citations) >= mid) {
                res = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return res;
    }
    private int f(int mid, int[] citations) {
        int count = 0;
        for (int i = 0; i < citations.length; i++) {
            if (citations[i] >= mid) {
                count++;
            }
        }
        return count;
    }

    /**
     * 二分答案法 九章模版
     *
     * @param citations
     * @return
     */
    public static int hIndex2(int[] citations) {
        if (citations.length == 1 && citations[0] == 0) {
            return 0;
        }
        Arrays.sort(citations);
        int len = citations.length;
        int left = 0;
        int right = len - 1;
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (citations[mid] >= len - mid) {
                right = mid;
            } else {
                left = mid;
            }
        }
        if (citations[left] == 0 && citations[right] == 0) {
            return 0;
        }
        if (citations[left] >= len - left) {
            return len - left;
        }
        return len - right;
    }

    /**
     * @param citations
     * @return
     */
    public static int hIndex1(int[] citations) {
        int len = citations.length;
        int total = 0;
        int[] res = new int[citations.length + 1];
        for (int i = 0; i < citations.length; i++) {
            if (citations[i] >= citations.length) {
                res[citations.length]++;
                continue;
            }
            res[citations[i]]++;
        }
        for (int i = citations.length; i > 0; i--) {
            total += res[i];
            //这里判断是关键
            if (i <= total) {
                return i;
            }
        }
        return 0;

    }

    public static void main(String[] args) {
        int[] citations = {3, 0, 6, 1, 5};
        int i = HIndex_274.hIndex1(citations);
        System.out.println(i);
    }


}
