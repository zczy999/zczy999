package leetcode;

import java.util.ArrayList;
import java.util.List;

public class generate_parentheses_22 {
    public static void main(String[] args) {
        generate_parentheses_22 res = new generate_parentheses_22();
        List<String> list = res.generateParenthesis(3);
        System.out.println(list.toString());

    }

    List<String> res = new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        dfs(1, 0, n, sb);
        return res;
    }

    private void dfs(int i, int j, int n, StringBuilder sb) {
        if (j > i || i > n) {
            return;
        }
        if (sb.length() == 2 * n) {
            res.add(sb.toString());
            return;
        }

        dfs(i+1,j,n,sb.append("("));
        sb.deleteCharAt(sb.length()-1);
        dfs(i,j+1,n,sb.append(")"));
        sb.deleteCharAt(sb.length()-1);

    }

}
