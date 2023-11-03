package leetcode;

import java.util.List;

public class Reverse_Linked_ListII_92 {
    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        Reverse_Linked_ListII_92 res = new Reverse_Linked_ListII_92();
        ListNode node = res.reverseBetween(node1, 2, 4);
        while (node != null) {
            System.out.println(node.val);
            node = node.next;
        }


    }

    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode sentinal = new ListNode(0);
        sentinal.next = head;
        ListNode pre = sentinal;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }
        ListNode cur = pre.next.next;
        ListNode end = pre.next;
        for (int i = 0; i < right - left; i++) {
            ListNode next = cur.next;
            cur.next = pre.next;
            pre.next = cur;
            cur = next;
        }
        end.next = cur;
        return sentinal.next;

    }

    public ListNode reverseBetween1(ListNode head, int left, int right) {
        if (right == 1) {
            return head;
        }
        ListNode preLeftNode = null, leftNode = null, rightNode = null, nextRightNode = null;
        int pos = -1;
        ListNode t = new ListNode(0);
        ListNode res = t;
        t.next = head;
        while (t != null) {
            pos++;
            if (pos == left - 1) {
                preLeftNode = t;
            }
            if (pos == left) {
                leftNode = t;
            }
            if (pos == right) {
                rightNode = t;
                nextRightNode = t.next;
            }
            t = t.next;
        }

        reverseList(leftNode, nextRightNode);
        preLeftNode.next = rightNode;
        leftNode.next = nextRightNode;


        return res.next;

    }

    public ListNode reverseList(ListNode head, ListNode end) {
        ListNode Sentinel = new ListNode(0);
        ListNode pre = head;
        while (pre != end) {
            ListNode cur = pre;
            pre = pre.next;
            cur.next = Sentinel.next;
            Sentinel.next = cur;
        }
        return Sentinel.next;

    }

    /**
     * 头插法
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        ListNode Sentinel = new ListNode(0);
        ListNode pre = head;
        while (pre != null) {
            ListNode cur = pre;
            pre = pre.next;
            cur.next = Sentinel.next;
            Sentinel.next = cur;
        }
        return Sentinel.next;

    }

    public ListNode reverseList1(ListNode head) {
        ListNode cur = null;
        ListNode pre = head;
        while (pre != null) {
            ListNode tem = pre.next;
            pre.next = cur;
            cur = pre;
            pre = tem;
        }
        return cur;

    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}