package ZuoGod.PrefixSum;

public class Range_Sum_Query_Immutable_303 {

    public int[] preSumNums;

    public Range_Sum_Query_Immutable_303(int[] nums) {
        preSumNums = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            preSumNums[i + 1] = preSumNums[i] + nums[i];
        }
    }

    public int sumRange(int left, int right) {
        return preSumNums[right+1] - preSumNums[left];
    }


}
