package zuoshen.DynamicProgramming;

public class Unique_Substrings_in_Wraparound_String_467 {
    public int findSubstringInWraproundString(String s) {
        int[] dp = new int[26];
        char[] charArray = s.toCharArray();
        int k = 1;
        dp[charArray[0] - 'a']++;
        for (int i = 1; i < charArray.length; i++) {
            int i1 = charArray[i] - 'a';
            int i2 = charArray[i - 1] - 'a';
            if (charArray[i] - charArray[i - 1] == 1
                    || (i1 == 0 && i2 == 25)) {
                k += 1;
                dp[i1] = Math.max(k, dp[i1]);
            } else {
                k = 1;
                dp[i1] = Math.max(k, dp[i1]);
            }
        }
        int sum = 0;
        for (int i : dp) {
            sum += i;
        }
        return sum;
    }

}
