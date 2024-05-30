package ZuoGod.UnionFind;

public class Similar_String_Groups_839 {

    public int numSimilarGroups(String[] strs) {
        UnionFind uf = new UnionFind(strs.length);
        for (int i = 0; i < strs.length; i++) {
            for (int j = i + 1; j < strs.length; j++) {
                if(similar(strs[i],strs[j])){
                    uf.union(i, j);
                }
            }
        }

        return uf.size;
    }

    private boolean similar(String str, String str1) {
        int res = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != str1.charAt(i)) {
                res++;
            }
            if (res > 2){
                return false;
            }
        }
        return res == 2 || res == 0;

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