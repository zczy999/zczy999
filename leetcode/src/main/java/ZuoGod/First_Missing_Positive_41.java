package ZuoGod;

public class First_Missing_Positive_41 {

    public int firstMissingPositive(int[] nums) {
        int left = 0, right = nums.length - 1;

        while (left < right) {
            if (nums[left] == left + 1) {
                left++;
            } else if (nums[left] <= left || nums[left] > right + 1 || nums[left] == nums[nums[left]-1]) {
                swap(left, right, nums);
                right--;
            } else {
                swap(left, nums[left] - 1, nums);
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return nums.length + 1;
    }

    private void swap(int left, int right, int[] nums) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }

}
