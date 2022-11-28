package leetcode;

import java.util.HashMap;

public class LRU_Cache_146 {

    public HashMap<Integer, Node> LRUMap;

    int capacity, nowCapacity;


    public Node sentinel;

    class Node {
        Node prev, next;
        int value;
        int key;

        public Node(int key, int value) {
            this.value = value;
            this.key = key;
        }
    }


    public LRU_Cache_146(int capacity) {
        nowCapacity = 0;
        this.capacity = capacity;
        this.LRUMap = new HashMap<>();
        this.sentinel = new Node(-1, -1);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    public int get(int key) {
        Node node = LRUMap.get(key);
        if (node == null) {
            return -1;
        }
        if (node.prev != sentinel) {
            // delete
            delete(node);
            // add
            addNew(node);
        }

        return node.value;
    }

    public void put(int key, int value) {
        Node node = LRUMap.get(key);

        if (node != null) {
            delete(node);
            nowCapacity--;
        }
        if (capacity == nowCapacity) {

            LRUMap.remove(sentinel.prev.key);
            delete(sentinel.prev);

            nowCapacity--;
        }

        node = new Node(key, value);
        LRUMap.put(key, node);
        addNew(node);


        nowCapacity++;

    }

    private void delete(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addNew(Node node) {
        node.next = sentinel.next;
        sentinel.next = node;
        node.prev = sentinel;
        node.next.prev = node;
    }

    public static void main(String[] args) {
        LRU_Cache_146 lRUCache = new LRU_Cache_146(2);
        lRUCache.put(1, 1); // 缓存是 {1=1}
        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
        System.out.println(lRUCache.get(1));    // 返回 1
        lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        System.out.println(lRUCache.get(2));
        //lRUCache.get(2);    // 返回 -1 (未找到)
        lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        System.out.println(lRUCache.get(1));
        //lRUCache.get(1);    // 返回 -1 (未找到)
        System.out.println(lRUCache.get(3));
        //lRUCache.get(3);    // 返回 3
        System.out.println(lRUCache.get(4));
        //lRUCache.get(4);    // 返回 4
    }
}
