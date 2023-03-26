package leetcode;

import java.util.ArrayList;
import java.util.List;

public class Number_of_IslandsII_305 {


    /**
     * 使用并查集完成
     * @param m
     * @param n
     * @param positions
     * @return
     */
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        UnionFind unionFind = new UnionFind(m * n);
        int[] visited = new int[m * n];
        List<Integer> result = new ArrayList<>();

        for (int[] pos : positions){
            int posRow = pos[0];
            int posLine = pos[1];
            int position = posRow * n + posLine;

            if(visited[position] != 1){
                unionFind.addNum();
                List<Integer> aroundPositions = getAroundPos(posRow, posLine, m, n, visited);
                for(int aroundPosition : aroundPositions){
                    if(!unionFind.isConnected(aroundPosition,position)){
                        unionFind.union(aroundPosition,position);
                    }
                }
                visited[position] = 1;
            }
            result.add(unionFind.getNum());
        }
        return result;
    }

    private List<Integer> getAroundPos(int posRow, int posLine, int m, int n, int[] visited) {
        List<Integer> list = new ArrayList<>();
        if (posRow - 1 >= 0 && visited[(posRow - 1) * n + posLine] == 1) {
            list.add((posRow - 1) * n + posLine);
        }
        if (posLine - 1 >= 0 && visited[(posRow) * n + (posLine - 1)] == 1) {
            list.add((posRow) * n + (posLine - 1));
        }
        if (posRow + 1 < m && visited[(posRow + 1) * n + posLine] == 1) {
            list.add((posRow + 1) * n + posLine);
        }
        if (posLine + 1 < n && visited[posRow * n + (posLine + 1)] == 1) {
            list.add(posRow * n + (posLine + 1));
        }
        return list;
    }


    class UnionFind {
        int[] pre;
        int num;

        public UnionFind(int n) {
            this.num = 0;
            this.pre = new int[n];
            for (int i = 0; i < n; i++) {
                pre[i] = i;
            }
        }

        public void union(int x, int y) {
            int xRoot = find(x);
            int yRoot = find(y);
            pre[yRoot] = xRoot;
            num--;
        }

        public int find(int x){
            if(x != pre[x]){
                pre[x] = find(pre[x]);
                return pre[x];
            }
            return x;
        }

        public boolean isConnected(int x, int y) {
            int xRoot = find(x);
            int yRoot = find(y);
            return xRoot == yRoot;
        }
        public void addNum() {
            num++;
        }
        public int getNum(){
            return this.num;
        }
    }

    public static void main(String[] args) {
//        Number_of_IslandsII_305 test = new Number_of_IslandsII_305();
//        UnionFind unionFind = new test.UnionFind(5);
//        unionFind.union(1,2);
//        unionFind.union(1,3);
//        unionFind.union(3,4);
//        System.out.println(unionFind.isConnected(1, 4));
    }

}
