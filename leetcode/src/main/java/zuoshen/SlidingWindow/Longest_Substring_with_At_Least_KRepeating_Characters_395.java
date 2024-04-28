package zuoshen.SlidingWindow;

public class Longest_Substring_with_At_Least_KRepeating_Characters_395 {

    public static void main(String[] args) {
        Longest_Substring_with_At_Least_KRepeating_Characters_395 res = new Longest_Substring_with_At_Least_KRepeating_Characters_395();
        String s = "aaabb";
        int i = res.longestSubstring(s, 3);
        System.out.println(i);
    }


    public int longestSubstring(String s, int k) {
        int res = 0;
        char[] charArray = s.toCharArray();
        for (int i = 1; i <= 26; i++) {
            int times = 0;
            int[] nums = new int[26];
            for (int left = 0, right = 0; right < s.length(); right++) {
                char ch = charArray[right];
                if (nums[ch - 'a'] == 0) {
                    times++;
                }
                nums[ch - 'a']++;

                while (times > i) {
                    ch = charArray[left];
                    left++;
                    nums[ch - 'a']--;
                    if (nums[ch - 'a'] == 0) {
                        times--;
                    }
                }

                if (check(nums, k)) {
                    res = Math.max(res, right - left + 1);
                }

            }
        }
        return res;
    }

    private boolean check(int[] nums, int k) {
        for (int num : nums) {
            if (num != 0 && num < k) {
                return false;
            }
        }
        return true;
    }

}
