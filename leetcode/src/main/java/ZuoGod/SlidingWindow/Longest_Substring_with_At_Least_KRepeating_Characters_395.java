package ZuoGod.SlidingWindow;

public class Longest_Substring_with_At_Least_KRepeating_Characters_395 {

    public static void main(String[] args) {
        Longest_Substring_with_At_Least_KRepeating_Characters_395 res = new Longest_Substring_with_At_Least_KRepeating_Characters_395();
        String s = "abcdedghijklmnopqrstuvwxyz";
        int i = res.longestSubstring(s, 2);
        System.out.println(i);
    }

    /**
     * 分治
     *
     * @param s
     * @param k
     * @return
     */
    public int longestSubstring(String s, int k) {
        return f(s, k, 0, s.length() - 1);
    }

    private int f(String s, int k, int start, int end) {
        if (end - start + 1 < k) {
            return 0;
        }
        int[] nums = new int[26];
        for (int i = start; i <= end; i++) {
            nums[s.charAt(i) - 'a']++;
        }
        int res = 0;
        for (int i = start; i <= end; i++) {
            if (nums[s.charAt(i) - 'a'] < k) {
                int j = i + 1;
                while (j <= end && nums[s.charAt(j) - 'a'] < k) {
                    j++;
                }

                return Math.max(f(s, k, start, i - 1), f(s, k, j, end));
            }
        }

        return end - start + 1;
    }


    /**
     * 滑动窗口解法
     *
     * @param s
     * @param k
     * @return
     */
    public int longestSubstring1(String s, int k) {
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
