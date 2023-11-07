package leetcode;

import java.util.*;

public class Evaluate_Division_399 {

    public static void main(String[] args) {
        Evaluate_Division_399 res = new Evaluate_Division_399();
        List<List<String>> equations = new ArrayList<>();
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        equations.add(list);
        list = new ArrayList<>();
        list.add("b");
        list.add("c");
        equations.add(list);
        double[] values = {2.0, 3.0};
        List<List<String>> queries = new ArrayList<>();
        list = new ArrayList<>();
        list.add("a");
        list.add("c");
        queries.add(list);
        list = new ArrayList<>();
        list.add("b");
        list.add("a");
        queries.add(list);
        list = new ArrayList<>();
        list.add("a");
        list.add("e");
        queries.add(list);
        double[] doubles = res.calcEquation(equations, values, queries);
        System.out.println(Arrays.toString(doubles));


    }

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, List<String>> map = new HashMap<>();
        Map<String, Double> valueMap = new HashMap<>();

        for (int i = 0; i < equations.size(); i++) {
            List<String> list = equations.get(i);
            double v = values[i];
            String first = list.get(0);
            String second = list.get(1);
            List<String> list1 = map.getOrDefault(first, new ArrayList<>());
            list1.add(second);
            map.put(first, list1);
            List<String> list2 = map.getOrDefault(second, new ArrayList<>());
            list2.add(first);
            map.put(second, list2);
            String fs = first + "-" + second;
            valueMap.put(fs, v);
            String sf = second + "-" + first;
            valueMap.put(sf, 1 / v);
        }

        double[] doubles = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            List<String> queryList = queries.get(i);
            String start = queryList.get(0);
            String end = queryList.get(1);
            List<String> res = new ArrayList<>();
            Set<String> set = new HashSet<>();
            List<String> dfs = dfs(start, end, map, res, set);
            if (dfs == null) {
                doubles[i] = -1;
                continue;
            }
            doubles[i] = 1;
            if (dfs.size() == 0) {
                continue;
            }
            for (String s : dfs) {
                doubles[i] *= valueMap.get(s);
            }

        }
        return doubles;


    }

    private List<String> dfs(String start, String end, Map<String, List<String>> map, List<String> res, Set<String> set) {
        if (!map.containsKey(start) || !set.add(start)) {
            return null;
        }
        if (end.equals(start)) {
            return res;
        }
        for (String s : map.get(start)) {
            res.add(start + "-" + s);
            List<String> dfs = dfs(s, end, map, res, set);
            if (dfs != null) {
                return res;
            }
            res.remove(res.size() - 1);
        }
        set.remove(start);
        return null;
    }

}
