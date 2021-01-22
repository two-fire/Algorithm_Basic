import java.util.HashSet;
import java.util.Set;

/**
 * 给定俩个可能有环也可能无环的单链表，头节点head1、head2。
 * 请实现函数，如果两个链表相交，返回相交的第一个节点；不相交返回null
 * 要求：如果两个链表长度之和为N,时间复杂度达到O(N)，额外空间复杂度O(1)
 *
 * 1）利用hashset 表中没有，把节点放入：
 *      当遇到的第一个表中存在的节点，即为第一个入环节点，返回它
 *      如果走到null，没有环，返回null
 *
 * 2）快慢指针
 *      slow：1步  fast：2步  在环中某处相遇
 *      假设环外有L个节点，环有R个节点
 *          slow：1步，fast：2步 第一次相遇，slow走过的路程为 R
 *          slow指针不动(记此时移动距离s)，fast指针指向head
 *          slow、fast同时移动，第一次相遇点即为入环节点
 *            原因：
 *              slow移动到入环节点处距离(L+R) - slow = L
 *              fast到入环节点距离也为L
 *
 * 如果相交，必为“Y”结构，即最后一定会汇合
 */
public class Code05_FindFirstIntersectNode {
    public static class Node {
        public int value;
        public Node next;

        public Node(int v) {
            value = v;
        }

    }


    /**
     * 主方法 返回两个链表第一个相交的节点
     * @param head1
     * @param head2
     * @return
     */
    public static Node getIntersectNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
//        Node loop1 = getLoopNode(head1);
//        Node loop2 = getLoopNode(head2);
        Node loop1 = getLoopNode1(head1);
        Node loop2 = getLoopNode1(head2);
        if (loop1 == null && loop2 == null) {
            return noLoop(head1, head2);
        }
        if (loop1 != null || loop2 != null) {
            return bothLoop(head1, loop1, head2, loop2);
        }
        return null;
    }

    /**
     * 用哈希表判断是否为有环链表
     * @param head
     * @return
     */
    public static Node getLoopNode1(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Set<Node> set = new HashSet<>();
        Node cur = head;
        while (cur != null) {
            if (set.contains(cur)) {
                return cur;
            }
            set.add(cur);
            cur = cur.next;
        }
        return null;
    }
    /**
     * 判断是否为有环链表
     * 是，返回第一个入环节点，否，返回null
     * @param head
     * @return
     */
    public static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        // n1 慢  n2 快
        Node n1 = head.next; // n1 -> slow
        Node n2 = head.next.next; // n2 -> fast
        // 快、慢指针第一次相遇，n1走过的路程为 R
        while (n1 != n2) {
            if (n2.next == null || n2.next.next == null) {
                return null;
            }
            n1 = n1.next;
            n2 = n2.next.next;
        }
        n2 = head; // n2 -> walk again from head
        while (n1 != n2) {
            n1 = n1.next;
            n2 = n2.next;
        }
        return n1;
    }

    /**
     * 如果两个链表都无环，返回第一个相交节点，如果不相交，返回null
     * @param head1
     * @param head2
     * @return 第一个相交的节点或者null
     */
    public static Node noLoop(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node cur1 = head1;
        Node cur2 = head2;
        // 计算两个链表的长度差 n
        int n = 0;
        while (cur1.next != null) {
            n++;
            cur1 = cur1.next;
        }
        while (cur2.next != null) {
            n--;
            cur2 = cur2.next;
        }
        // 尾节点地址不同，一定不相交
        if (cur1 != cur2) {
            return null;
        }
        cur1 = n > 0 ? head1 : head2; // 长一点的链表的头
        cur2 = cur1 == head1 ? head2 : head1; // 短一点的链表的头
        // cur2 先走 n 步   一定在短链表的长度内相交
        // cur1、cur2 一起移动，第一次相遇即为入环节点
        n = Math.abs(n);
        while (n-- != 0) {
            cur1 = cur1.next;
        }
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    /**
     * 两个有环链表，返回第一个相交节点，如果不相交返回null
     * @param head1
     * @param loop1 入环节点
     * @param head2
     * @param loop2 入环节点
     * @return
     */
    public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        Node cur1 = null;
        Node cur2 = null;
        // 1. 一个相交节点：loop1 = loop2，返回loop1
        if (loop1 == loop2) {
            cur1 = head1;
            cur2 = head2;
            int n = 0;
            while (cur1 != loop1) {
                n++;
                cur1 = cur1.next;
            }
            while (cur2 != loop2) {
                n--;
                cur2 = cur2.next;
            }
            cur1 = n > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 :head1;
            n = Math.abs(n);
            while (n-- != 0) {
                cur1 = cur1.next;
            }
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        } else {  // loop1 ≠ loop2
            cur1 = loop1.next;
            while (cur1 != loop1) {
                // 2. 两个相交节点：loop1往下走，遇到loop2，返回loop1或loop2
                if (cur1 == loop2) {
                    return loop1;
                }
                cur1 = cur1.next;
            }
            // 3. 各自成环：loop1往下走，但一直遇不到loop2，返回空
            return null;
        }
    }

    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value); // 无环相交

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2->3->4->5->6->7->4...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getIntersectNode(head1, head2).value); // 情况1:  2

        // 0->9->8->6->7->4->5->6->7->4...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value); // 情况2: 4或者6
    }

}
