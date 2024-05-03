package zuoshen;

public class Find_the_Duplicate_Number_287 {

    public int findDuplicate(int[] nums) {
        int slow = 0, fast = 0;
        int occur;
        while (true) {
            fast = nums[nums[fast]];
            slow = nums[slow];
            if (fast == slow) {
                occur = fast;
                break;
            }
        }
        int res = 0;
        while (true) {
            res = nums[res];
            occur = nums[occur];
            if (res == occur) {
                break;
            }
        }
        return res;
    }

}
