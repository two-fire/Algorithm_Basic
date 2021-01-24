import java.util.LinkedList;

/**
 * Complete Binary Tree 完全二叉树
 * 给定一颗二叉树的头节点head，判断它是否为CBT:
 * 方法一：
 *  1. 任何节点有右孩子无左孩子，false；否则继续
 *  2. 一旦遇到左右孩子不双全，后续遇到所有节点必须为叶子节点
 *
 * 方法二：
 *  1. 满二叉树 无缺口
 *      向左树要：你是否是满的以及你整体的高度
 *      向右树要：你是否是满的以及你整体的高度
 *      当左树右树都满且高度一样符合情况1
 *  2. 有缺口
 *      1）成长到最后一层，缺口并没有越过我的左树
 *          向左树要：你整体是否是完全二叉树以及你的高度
 *          向左树要：你整体是否是满二叉树以及你的高度
 *          当左树高度比右树高度大一，且左树为CBT，右树是满二叉树，符合情况2
 *      2）左树上满了，缺口来到了我的右端
 *          向左树要：你整体是否是完全二叉树以及你的高度
 *          向左树要：你整体是否是满二叉树以及你的高度
 *          当左树右树高度一样，且左树满，右树是CBT，符合情况3
 *
 *  综上，需要向子树要的信息：是否是满二叉树，是否是CBT，你的高度
 */
public class Code06_IsCBT {
    public static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static boolean isCBT1(Node head) {
        if (head == null) {
            return true;
        }
        // 创建队列用来宽度优先遍历
        LinkedList<Node> queue = new LinkedList<>();
        // 是否遇到孩子不双全的节点
        Boolean leaf = false;
        Node l = null;
        Node r = null;
        queue.add(head);
        while (!queue.isEmpty()) {
            head = queue.poll();
            l = head.left;
            r = head.right;
            // 1. 有右无左 2.  遇到不双全后，又发现当前节点不是叶节点
            if (r != null && l == null || (leaf && !(l == null && r == null))) {
                return false;
            }
            if (l != null) {
                queue.add(l);
            }
            if (r != null) {
                queue.add(r);
            }
            if (r == null || l == null) { // 不能放到最前面，因为第一个不双全的节点后才要进行2. 判断
                leaf = true;
            }
        }
        return true;
    }

    // 对于每棵子树，是否是满二叉树，是否是CBT，你的高度
    public static class Info {
        private boolean isFull;
        private boolean isCBT;
        private int height;

        public Info(boolean isFull, boolean isCBT, int height) {
            this.isFull = isFull;
            this.isCBT = isCBT;
            this.height = height;
        }
    }

    public static boolean isCBT2(Node head) {
        Info process = process(head);
        return process.isCBT;
    }

    /**
     * 对于任何节点X一定要返回三个信息
     * @param X
     * @return
     */
    public static Info process(Node X) {
        if (X == null) {
            return new Info(true, true, 0);
        }
        Info leftInfo = process(X.left);
        Info rightInfo = process(X.right);
        // 整合节点的三个信息
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isFull = false;
        if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height) {
            isFull = true;
        }
        boolean isCBT = false;
        if (isFull) {
            isCBT = true;
        } else { // 以x为头整棵树，不满
            if (leftInfo.height - 1 == rightInfo.height && leftInfo.isCBT && rightInfo.isFull) {
                isCBT = true;
            }
            if (leftInfo.height == rightInfo.height && leftInfo.isFull && rightInfo.isCBT) {
                isCBT = true;
            }
        }
        return new Info(isFull, isCBT, height);
    }
    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isCBT1(head) != isCBT2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}