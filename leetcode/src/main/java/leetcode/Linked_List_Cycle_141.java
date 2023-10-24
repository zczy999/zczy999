package leetcode;

public class Linked_List_Cycle_141 {
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null){
            return false;
        }
            ListNode fastNode = head, slowNode = head;
        while (fastNode != null && fastNode.next != null) {
            fastNode = fastNode.next.next;
            slowNode = slowNode.next;

            if (slowNode == fastNode) {
                return true;
            }
        }
        return false;

    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

}
