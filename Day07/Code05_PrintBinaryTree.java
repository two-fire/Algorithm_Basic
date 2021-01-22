/**
 *  设计一个打印整棵树的打印函数
 */
public class Code05_PrintBinaryTree {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    /**
     * 打印一棵二叉树
     * @param head
     */
    public static void printTree(Node head) {
        System.out.println("Binary Tree: ");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    /**
     * 按照右头左(即中序的逆序)顺序进行打印
     * @param head   父节点
     * @param height 节点所在层数
     * @param to     标识 H: 头节点 ^: 向上连接的左节点  v: 右节点
     * @param len    预留一个节点所占的长度
     */
    private static void printInOrder(Node head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", 17);
        String val = to + head.value + to;
        int lenL = (len - val.length()) / 2;
        int lenR = len - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        // 前面留空格的数量是与节点的深度相关的
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", 17);
    }
    private static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer();
        while (num-- != 0) {
            buf.append(space);
        }
        return buf.toString();
    }
    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(-222222222);
        head.right = new Node(3);
        head.left.left = new Node(Integer.MIN_VALUE);
        head.right.left = new Node(55555555);
        head.right.right = new Node(66);
        head.left.left.right = new Node(777);
        printTree(head);

        head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.right.left = new Node(5);
        head.right.right = new Node(6);
        head.left.left.right = new Node(7);
        printTree(head);

        head = new Node(1);
        head.left = new Node(1);
        head.right = new Node(1);
        head.left.left = new Node(1);
        head.right.left = new Node(1);
        head.right.right = new Node(1);
        head.left.left.right = new Node(1);
        printTree(head);

    }

}
