package ZuoGod.DynamicProgramming;

public class Swap_Nodes_in_Pairs_24 {

    public ListNode swapPairs(ListNode head) {
        return f(head);
    }

    public ListNode f(ListNode head) {
        if (head == null || head.next == null){
            return head;
        }
        ListNode next = head.next;
        head.next = f(next.next);
        next.next = head;
        return next;
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
