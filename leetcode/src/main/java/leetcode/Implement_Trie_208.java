package leetcode;

public class Implement_Trie_208 {

    public static void main(String[] args) {
        Implement_Trie_208 res = new Implement_Trie_208();
        res.insert("apple");
        boolean apple = res.search("apple");
        boolean app = res.startsWith("app");
        System.out.println(apple);
        System.out.println(app);
    }

    private TrieNode root;

    public Implement_Trie_208() {
        root = new TrieNode();
        root.isWord = false;
    }

    public void insert(String word) {
        TrieNode cur = root;
        char[] charArray = word.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            TrieNode child = cur.children[charArray[i] - 'a'];
            if (child == null) {
                cur.children[charArray[i] - 'a'] = new TrieNode();
            }
            cur = cur.children[charArray[i] - 'a'];
        }
        cur.isWord = true;
    }

    public boolean search(String word) {
        TrieNode cur = root;
        char[] charArray = word.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            TrieNode child = cur.children[charArray[i] - 'a'];
            if (child == null) {
                return false;
            }
            cur = child;
        }
        return cur.isWord;

    }

    public boolean startsWith(String prefix) {
        TrieNode cur = root;
        char[] charArray = prefix.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            TrieNode child = cur.children[charArray[i] - 'a'];
            if (child == null) {
                return false;
            }
            cur = child;
        }
        return true;


    }

    class TrieNode {

        boolean isWord;
        TrieNode[] children = new TrieNode[26];
    }
}
