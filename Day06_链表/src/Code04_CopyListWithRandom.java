import java.util.HashMap;

/**
 * 一种特殊的单链表节点类型（含rand指针，指向表中任一节点，也可以指向null）
 *  给定一个无环单链表的头节点head，请完成这个链表的复制
 *  要求：时间复杂度O(N) 额外空间复杂度O(1)
 * 1）笔试：哈希表
 *      遍历链表，把老节点、克隆的新节点存到map中
 * 2）面试：把克隆的新节点插入到对应的老节点后面
 *      人为构造对应关系，所以不需要哈希表来 --> 先解决拷贝节点的rand指针
 *      老、克隆链表分离
 */

public class Code04_CopyListWithRandom {
    public static class Node {
        public int value;
        public Node next;
        public Node rand;

        public Node(int v) {
            value = v;
        }
    }

    public static Node copyListWithRand1(Node head) {
        if (head == null) {
            return null;
        }
        HashMap<Node, Node> map = new HashMap<>();
        Node cur = head;
        while (cur != null) {
            map.put(cur, new Node(cur.value));
            cur = cur.next;
        }
        cur = head;
        while (cur != null) {
            map.get(cur).next = cur.next;
            map.get(cur).rand = cur.rand;
            cur = cur.next;
        }
        return map.get(head);
    }

    public static Node copyListWithRand2(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        Node next = null;
        // copy Node and link to every node
        // 1 -> 2
        // 1 -> 1' -> 2
        while (cur != null) {
            // cur 老   next 老节点的下一个
            next = cur.next;
            cur.next = new Node(cur.value);
            cur.next.next = next;
            cur = next;
        }
        // rand指针
        // 1 -> 1' -> 2 -> 2'
        // 每两个为一组遍历
        cur = head;
        Node curCopy = null;
        while (cur != null) {
            next = cur.next.next;
            curCopy = cur.next;
            curCopy.rand = cur.rand == null ? null : cur.rand.next;
            cur = next;
        }
        // split
        Node res = head.next;
        cur = head;
        while (cur != null) {
            next = cur.next.next;
            curCopy = cur.next;
            cur.next = next;
            curCopy.next = next == null ? null : next.next;
            cur = cur.next;
        }
        return res;
    }

    public static void printRandLinkedList(Node head) {
        Node cur =head;
        System.out.println("order: ");
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println();
        cur = head;
        System.out.println("rand: ");
        while (cur != null) {
            System.out.print(cur.rand == null ? "-" : cur.rand.value + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = null;
        Node res1 = null;
        Node res2 = null;
        printRandLinkedList(head);
        res1 = copyListWithRand1(head);
        printRandLinkedList(res1);
        res2 = copyListWithRand2(head);
        printRandLinkedList(res2);
        printRandLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);

        head.rand = head.next.next.next.next.next; // 1 -> 6
        head.next.rand = head.next.next.next.next.next; // 2 -> 6
        head.next.next.rand = head.next.next.next.next; // 3 -> 5
        head.next.next.next.rand = head.next.next; // 4 -> 3
        head.next.next.next.next.rand = null; // 5 -> null
        head.next.next.next.next.next.rand = head.next.next.next; // 6 -> 4

        System.out.println("原始链表：");
        printRandLinkedList(head);
        System.out.println("=========================");
        res1 = copyListWithRand1(head);
        System.out.println("方法一的拷贝链表：");
        printRandLinkedList(res1);
        System.out.println("=========================");
        res2 = copyListWithRand2(head);
        System.out.println("方法二的拷贝链表：");
        printRandLinkedList(res2);
        System.out.println("=========================");
        System.out.println("经历方法二拷贝之后的原始链表：");
        printRandLinkedList(head);
        System.out.println("=========================");

    }

}
