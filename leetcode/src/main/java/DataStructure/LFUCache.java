package DataStructure;

import java.util.*;

/**
 * https://leetcode.cn/problems/lfu-cache/description/
 */
public class LFUCache {

    int minfreq, capacity;
    Map<Integer, Node> keyTable;
    Map<Integer, DoublyLinkedList> freqTable;


    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minfreq = 0;
        this.keyTable = new HashMap<>(capacity);
        this.freqTable = new HashMap<>();
    }


    public int get(int key) {
        if (capacity == 0){
            return -1;
        }
        if (!keyTable.containsKey(key)){
            return -1;
        }
        Node curNode = keyTable.get(key);
        int freq = curNode.freq;
        freqTable.get(freq).remove(curNode);
        if (freqTable.get(freq).size == 0) {
            freqTable.remove(freq);
            if (minfreq == freq) {
                minfreq += 1;
            }
        }
        Node newNode = new Node(key, curNode.val, freq + 1);
        DoublyLinkedList newFreqList = freqTable.getOrDefault(freq + 1, new DoublyLinkedList());
        newFreqList.addFirst(newNode);
        freqTable.put(freq + 1, newFreqList);
        keyTable.put(key, newNode);
        return newNode.val;
    }

    public void put(int key, int value) {
        if (capacity == 0){
            return;
        }
        if (!keyTable.containsKey(key)) {
            // 容量满了，删除最久没有被访问的节点
            if (capacity == keyTable.size()) {
                DoublyLinkedList minfreqList = freqTable.get(minfreq);
                Node tail = minfreqList.getTail();
                keyTable.remove(tail.key);
                minfreqList.remove(tail);
                if (minfreqList.size == 0) {
                    freqTable.remove(minfreq);
                }
            }
            Node cur = new Node(key, value, 1);
            keyTable.put(key, cur);
            DoublyLinkedList curFreqList = freqTable.getOrDefault(1, new DoublyLinkedList());
            freqTable.put(1, curFreqList);
            curFreqList.addFirst(cur);
            minfreq = 1;
        } else {
            Node cur = keyTable.get(key);
            int freq = cur.freq;
            freqTable.get(freq).remove(cur);
            if (freqTable.get(freq).size == 0) {
                freqTable.remove(freq);
                if (minfreq == freq) {
                    minfreq += 1;
                }
            }
            Node newNode = new Node(key, value, freq + 1);
            keyTable.put(key, newNode);
            DoublyLinkedList newFreqList = freqTable.getOrDefault(freq + 1, new DoublyLinkedList());
            freqTable.put(freq + 1, newFreqList);
            newFreqList.addFirst(newNode);
        }
    }


    class Node {
        int key, val, freq;
        Node prev, next;

        Node() {
            this(-1, -1, 0);
        }

        Node(int key, int val, int freq) {
            this.key = key;
            this.val = val;
            this.freq = freq;
        }
    }

    class DoublyLinkedList {
        Node dummyHead, dummyTail;
        int size;

        DoublyLinkedList() {
            dummyHead = new Node();
            dummyTail = new Node();
            dummyHead.next = dummyTail;
            dummyTail.prev = dummyHead;
            size = 0;
        }

        public void addFirst(Node node) {
            Node prevHead = dummyHead.next;
            node.prev = dummyHead;
            dummyHead.next = node;
            node.next = prevHead;
            prevHead.prev = node;
            size++;
        }

        public void remove(Node node) {
            Node prev = node.prev, next = node.next;
            prev.next = next;
            next.prev = prev;
            size--;
        }

        public Node getHead() {
            return dummyHead.next;
        }

        public Node getTail() {
            return dummyTail.prev;
        }

    }
}