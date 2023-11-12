package leetcode;

import java.util.*;

public class Minimum_Genetic_Mutation_433 {

    public static void main(String[] args) {
        String startGene = "AACCGGTT";
        String endGene = "AACCGGTA";
        String[] bank = {"AACCGGTA"};
        Minimum_Genetic_Mutation_433 res = new Minimum_Genetic_Mutation_433();
        int i = res.minMutation(startGene, endGene, bank);
        System.out.println(i);

    }




    private Set<String> set = new HashSet<>();
    int min = Integer.MAX_VALUE;

    Map<String, List<String>> graph = new HashMap<>();

    /**
     * 建图+dfs+最短路径
     * @param startGene
     * @param endGene
     * @param bank
     * @return
     */
    public int minMutation(String startGene, String endGene, String[] bank) {
        for (int i = 0; i < bank.length; i++) {
            List<String> list = findNext(bank[i], bank);
            graph.put(bank[i], list);
        }
        graph.put(startGene, findNext(startGene, bank));

        dfs(startGene, endGene, 0);

        return min == Integer.MAX_VALUE ? -1 : min;


    }

    private void dfs(String startGene, String endGene, int i) {
        if (startGene.equals(endGene)) {
            min = Math.min(i, min);
            return;
        }
        if (!set.add(startGene) || i > min) {
            return;
        }
        for (String s : graph.get(startGene)) {
            dfs(s, endGene, i + 1);
        }
        set.remove(startGene);
    }

    private List<String> findNext(String s, String[] bank) {
        List<String> res = new ArrayList<>();
        for (String b : bank) {
            if (b.equals(s)) {
                continue;
            }
            int flag = 0;
            for (int i = 0; i < b.length(); i++) {
                if (flag > 1) {
                    break;
                }
                if (b.charAt(i) != s.charAt(i)) {
                    flag++;
                }
            }
            if (flag == 1) {
                res.add(b);
            }
        }
        return res;
    }

}
