package leetcode;

import java.util.Arrays;

public class HIndex_274 {
    public static int hIndex(int[] citations) {
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

    public static void main(String[] args) {
        int[] citations = {3, 0, 6, 1, 5};
        int i = HIndex_274.hIndex(citations);
        System.out.println(i);
    }


}
