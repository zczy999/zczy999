package lintcode;

public class KClosestNumbersInSortedArray_460 {

    public int[] KClosestNumbers(int[] array, int target, int k) {


        return null;
    }

    private int findLowerCloseNumber(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (target <= array[mid]) {
                right = mid;
            } else {
                left = mid;
            }
        }
        if (array[right] < target) {
            return right;
        }
        if (array[left] < target) {
            return left;
        }
        return -1;
    }

    private int findUperCloseNumber(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (target >= array[mid]) {
                left = mid;
            } else {
                right = mid;
            }
        }
        if (array[left] > target) {
            return left;
        }
        if (array[right] > target) {
            return right;
        }
        return -1;
    }

    public static void main(String[] args) {
        KClosestNumbersInSortedArray_460 kClosestNumbersInSortedArray_460 = new KClosestNumbersInSortedArray_460();
//        int[] test = {0, 1, 1, 1, 2, 2, 3, 3, 3, 4, 5, 6};
        int[] test = {0, 1, 1, 2, 2, 4, 5, 6, 9, 10};
//        int[] test = {1, 3, 3, 3, 3, 3, 4, 5, 6};
//        int lowerCloseNumber = kClosestNumbersInSortedArray_460.findLowerCloseNumber(test, 3);
//        System.out.println(lowerCloseNumber);
        int uperCloseNumber = kClosestNumbersInSortedArray_460.findUperCloseNumber(test, 3);
        System.out.println(uperCloseNumber);

    }

}
