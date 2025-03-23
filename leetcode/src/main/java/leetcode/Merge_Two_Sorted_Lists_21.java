package leetcode;

public class Merge_Two_Sorted_Lists_21 {

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        return f(list1, list2);
    }

    private ListNode f(ListNode list1, ListNode list2) {
        if (list1 == null){
            return list2;
        }
        if (list2 == null){
            return list1;
        }
        if (list1.val < list2.val){
            list1.next = f(list1.next, list2);
            return list1;
        }else {
            list2.next = f(list1, list2.next);
            return list2;
        }
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
