package leetcode;

public class Zigza_Conversion_6 {
    public static void main(String[] args) {
//        String s = "PAYPALISHIRING";
        String s = "ab";
        String convert = Zigza_Conversion_6.convert(s, 1);
        System.out.println(convert);
    }

    public static String convert(String s, int numRows) {
        if (numRows == 1){
            return s;
        }
        StringBuilder sb = new StringBuilder();
        int[] flag = new int[s.length()];
        boolean dir = true;
        int n = 0;
        for (int i = 0; i < s.length(); i++) {
            if (dir) {
                flag[i] = ++n;
                if (n == numRows) {
                    dir = false;
                }
            } else {
                flag[i] = --n;
                if (n == 1) {
                    dir = true;
                }
            }
        }

        for (int i = 1; i <= numRows; i++) {
            for (int j = 0; j < flag.length; j++) {
                if (flag[j] == i){
                    sb.append(s.charAt(j));
                }
            }
        }
        return sb.toString();
    }
}
