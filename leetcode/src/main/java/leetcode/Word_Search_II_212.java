package leetcode;

import java.util.ArrayList;
import java.util.List;

public class Word_Search_II_212 {

    int[][] direction = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    int[][] flag;
    Trie trie;

    public List<String> findWords(char[][] board, String[] words) {
        this.trie = new Trie();
        for (String word : words) {
            trie.insert(word);
        }

        List<String> ans = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, trie.getRoot(), ans);
            }
        }

        return ans;
    }

    private void dfs(char[][] board, int i, int j, Trie.TrieNode curNode, List<String> ans) {
        // 越界 或者 走了回头路，直接返回0
        if (i < 0 || i == board.length || j < 0 || j == board[0].length || board[i][j] == 0) {
            return;
        }

        char tmp = board[i][j];
        if (tmp == '#') {
            return;
        }
        Trie.TrieNode newNode = curNode.nexts[tmp - 'a'];
        if (newNode == null || newNode.pass == 0) {
            return;
        }
        if (newNode.end > 0) {
            ans.add(newNode.word);
            // 直接标记为0，防止重复添加
            newNode.end = 0;
            //递归过程中不要物理删除 Trie 节点，逻辑标记（end=0）已足够高效且安全。
            //物理剪枝适合非递归或特殊场景，绝大多数情况下推荐安全优先。
            //trie.erase(newNode.word);
        }
        board[i][j] = '#';
        for (int[] dir : direction) {
            dfs(board, i + dir[0], j + dir[1], newNode, ans);
        }
        board[i][j] = tmp;
    }

    public class Trie {

        class TrieNode {
            public int pass;
            public int end;
            public String word;
            public TrieNode[] nexts;

            public TrieNode() {
                pass = 0;
                end = 0;
                nexts = new TrieNode[26];
                word = null;
            }
        }

        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            if (word == null || word.length() == 0) {
                return;
            }
            TrieNode cur = root;
            cur.pass++;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (cur.nexts[c - 'a'] == null) {
                    cur.nexts[c - 'a'] = new TrieNode();
                }
                cur = cur.nexts[c - 'a'];
                cur.pass++;
            }
            cur.end++;
            cur.word = word;
        }

        // 如果之前word插入过前缀树，那么此时删掉一次
        // 如果之前word没有插入过前缀树，那么什么也不做
        public void erase(String word) {
            TrieNode cur = root;
            cur.pass--;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                // 先检查节点是否存在
                if (cur.nexts[c - 'a'] == null) {
                    return;
                }
                if (--cur.nexts[c - 'a'].pass == 0) {
                    cur.nexts[c - 'a'] = null;
                    return;
                }
                cur = cur.nexts[c - 'a'];
            }
            cur.end--;

        }


        public TrieNode getRoot() {
            return root;
        }

    }


}
