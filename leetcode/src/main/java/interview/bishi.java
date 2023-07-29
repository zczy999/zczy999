package interview;

import java.util.ArrayList;

/**
 * 招商笔试
 */
public class bishi {
    public static void main(String[] args) {
    }

    public static int count(int L, int R, int x) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = L; i <= R; i++) {
            stringBuilder.append(i);
        }
        String numString = stringBuilder.toString();
        char xStr = (char) (x + 48);
        int count = 0;
        for (int i = 0; i < numString.length(); i++) {
            if (numString.charAt(i) == xStr) {
                count++;
            }
        }
        return count;
    }

    public ArrayList<Integer> extraNum(String s) {
        // write code here
        ArrayList<Integer> res = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                stringBuilder.append(s.charAt(i));
            } else if (stringBuilder.length() > 0) {
                int temp = Integer.parseInt(stringBuilder.toString());
                res.add(temp);
                stringBuilder.setLength(0);
            }
        }
        if (stringBuilder.length() > 0) {
            int temp = Integer.parseInt(stringBuilder.toString());
            res.add(temp);
        }
        return res;
    }
}