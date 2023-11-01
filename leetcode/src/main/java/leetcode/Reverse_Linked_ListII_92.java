package leetcode;

import java.util.List;

public class Reverse_Linked_ListII_92 {
    public ListNode reverseBetween(ListNode head, int left, int right) {


    }


    /**
     * 头插法
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        ListNode Sentinel = new ListNode(0);
        ListNode pre = head;
        while (pre != null){
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
        while (pre != null){
            ListNode tem = pre.next;
            pre.next = cur;
            cur = pre;
            pre = tem;
        }
        return cur;

    }

    public class ListNode {
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