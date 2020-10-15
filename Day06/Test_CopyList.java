import java.util.List;

public class Test_CopyList {
    public static class Node {
        public int value;
        public Node next;

        public Node(int v) {
            value = v;
        }
    }
    public static Node copyList(Node head) {
        Node cur = head;
        Node headCopy = new Node(cur.value);
        Node curCopy = headCopy;
        while (cur.next != null) {
//            curCopy会成为指向cur的引用，而非拷贝cur
//            Node curCopy;
//            curCopy = cur;

            curCopy.next = new Node(cur.next.value);
            cur = cur.next;
            curCopy = curCopy.next;
        }
        curCopy.next = null;
        return headCopy;
    }

    public static void printLinkedList(Node head) {
        Node cur = head;
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println();
    }
    public static void main(String[] args) {
        Node head = null;
        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);

        System.out.println("原始链表：");
        printLinkedList(head);
        System.out.println("=========================");
        System.out.println("拷贝链表：");
        printLinkedList(copyList(head));
    }




}
