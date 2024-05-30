package ZuoGod.UnionFind;

import java.util.HashMap;
import java.util.Map;

public class Most_Stones_Removed_947 {

    public int removeStones(int[][] stones) {
        Map<Integer, Integer> rowMap = new HashMap<>();
        Map<Integer, Integer> colMap = new HashMap<>();

        UnionFind unionFind = new UnionFind(stones.length);
        for (int i = 0; i < stones.length; i++) {
            int row = stones[i][0];
            int col = stones[i][1];
            if (!rowMap.containsKey(row)) {
                rowMap.put(row, i);
            } else {
                unionFind.union(i,rowMap.get(row));
            }
            if (!colMap.containsKey(col)) {
                colMap.put(col, i);
            } else {
                unionFind.union(i,colMap.get(col));
            }
        }
        return stones.length - unionFind.size;
    }


    private class UnionFind {
        int[] parent;
        int size;

        public UnionFind(int size) {
            parent = new int[size];
            this.size = size;
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public int find(int p) {
            if (parent[p] != p) {
                parent[p] = find(parent[p]);
            }
            return parent[p];
        }

        public void union(int p, int q) {
            if (find(p) != find(q)) {
                parent[find(p)] = find(q);
                size--;
            }
        }

        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }

    }
}
