package ZuoGod.UnionFind;

public class Couples_Holding_Hands_765 {


    public int minSwapsCouples(int[] row) {
        UnionFind uf = new UnionFind(row.length / 2);
        for (int i = 0; i < row.length; i = i + 2) {
            uf.union(row[i] / 2, row[i + 1] / 2);
        }

        return row.length / 2 - uf.size;
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
