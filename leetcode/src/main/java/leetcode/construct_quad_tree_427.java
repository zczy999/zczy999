package leetcode;

public class construct_quad_tree_427 {

    public static void main(String[] args) {
        construct_quad_tree_427 res = new construct_quad_tree_427();
        int[][] grid = {{0,1},{1,0}};
        Node construct = res.construct(grid);
        System.out.println(construct.toString());
    }

    public Node construct(int[][] grid) {
        int n = grid.length - 1;
        int m = grid[0].length - 1;
        return construct(grid, 0, n, 0, m);
    }

    private Node construct(int[][] grid, int i, int n, int j, int m) {
        Node res = new Node();
        if (checkGrid(grid, i, n, j, m)) {
            res.val = grid[i][j] == 1;
            res.isLeaf = true;
            return res;
        }
        res.isLeaf = false;
        res.val = true;
        int midi = (i + n) / 2;
        int midj = (j + m) / 2;
        res.topLeft = construct(grid, i, midi, j, midj);
        res.topRight = construct(grid, i, midi, midj + 1, m);
        res.bottomLeft = construct(grid, midi + 1, n, j, midj);
        res.bottomRight = construct(grid, midi + 1, n, midj + 1, m);
        return res;
    }

    private boolean checkGrid(int[][] grid, int i, int n, int j, int m) {
        if (i == n && j == m) {
            return true;
        }
        int cur = grid[i][j];
        for (int k = i; k <= n; k++) {
            for (int l = j; l <= m; l++) {
                if (grid[k][l] != cur) {
                    return false;
                }
            }
        }
        return true;
    }

}


// Definition for a QuadTree node.
class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;


    public Node() {
        this.val = false;
        this.isLeaf = false;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }

    public Node(boolean val, boolean isLeaf) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }

    public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }
};

