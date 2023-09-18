package leetcode;

public class Integer_to_Roman_12 {
    public String intToRoman(int num) {
        int[] integers = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanNumerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < integers.length; i++) {
            while (num >= integers[i]) {
                num = num - integers[i];
                sb.append(romanNumerals[i]);
            }
        }
        return sb.toString();
    }
}
