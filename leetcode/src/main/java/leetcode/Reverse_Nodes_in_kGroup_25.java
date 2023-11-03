package leetcode;

public class Reverse_Nodes_in_kGroup_25 {
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
        Reverse_Nodes_in_kGroup_25 res = new Reverse_Nodes_in_kGroup_25();
        ListNode node = res.reverseKGroup(node1, 2);
        while (node != null) {
            System.out.println(node.val);
            node = node.next;
        }
    }


    public ListNode reverseKGroup(ListNode head, int k) {
        int num = 0;
        int left = 0, right = 0;
        ListNode cur = head, res = null;
        while (head != null) {
            num++;
            if ((num - 1) % k == 0) {
                left = num;
            }
            head = head.next;
            if (num % k == 0) {
                right = num;
                ListNode temp = reverseBetween(cur, left, right);
                if (left == 1) {
                    res = temp;
                    cur = res;
                }
            }
        }
        return res;
    }

    private ListNode reverseBetween(ListNode head, int left, int right) {
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
