package leetcode;

public class Length_of_Last_Word_58 {
    public int lengthOfLastWord(String s) {
        int len = 0;
        boolean first = true;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == ' ' && first) {
                continue;
            }
            if (s.charAt(i) != ' '){
                len++;
                first = false;
            }
            if (!first && s.charAt(i) == ' '){
                break;
            }
        }
        return len;
    }
}
