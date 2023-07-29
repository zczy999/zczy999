package leetcode;

public class Merge_Sorted_Array_88 {
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] res = new int[m + n];
        int i = 0;
        int j = 0;
        int cur = 0;
        while (i < m && j < n) {
            if (nums1[i] <= nums2[j]) {
                res[cur++] = nums1[i++];
            } else {
                res[cur++] = nums2[j++];
            }
        }
        while (i < m) {
            res[cur++] = nums1[i++];
        }
        while (j < n) {
            res[cur++] = nums2[j++];
        }
        for (int k = 0; k < m + n; k++) {
            nums1[k] = res[k];
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 3, 0, 0, 0};
        int[] nums2 = {2, 5, 6};
        Merge_Sorted_Array_88.merge(nums1, 4, nums2, 3);

    }
}
