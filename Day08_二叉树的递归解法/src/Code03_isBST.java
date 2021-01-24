import java.util.ArrayList;

/**
 * 给定一颗二叉树的头节点head，返回这棵二叉树是不是二叉搜索树
 *  【经典二叉搜索树：BST 没有重复值，左树比它小，右树比它大】
 *
 * 方法一：中序遍历发现序列一直在上升就是BST
 *
 * 方法二：
 * 可能性：
 * 1）左树必须是BST
 * 2）右树必须是BST
 * 3）左树最大值比我小
 * 4）右树最小值比我大
 *
 * 列出向左右子树要的信息：
 * 1）X左 搜否？
 * 2）X右 搜否？
 * 3）X左 max
 * 4）X右 min
 *
 * 信息求全集：
 * 1）搜否？
 * 2）max
 * 3）min
 */
public class Code03_isBST {
    public static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static boolean isBST1(Node head) {
        if (head == null) {
            return true;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 0; i < arr.size() - 1; i++) {
            if (arr.get(i).value >= arr.get(i + 1).value) {
                return false;
            }
        }
        return true;
    }

    /**
     * 中序遍历
     * @param head
     * @param arr
     */
    private static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        if (head.left != null) {
            in(head.left, arr);
        }
        arr.add(head);
        if (head.right != null) {
            in(head.right, arr);
        }
    }

    public static boolean isBST2(Node head) {
        if (head == null) {
            return true;
        }
        return process(head).isBST;
    }

    public static class Info {
        private boolean isBST;
        private int max;
        private int min;

        public Info(boolean isBST, int max, int min) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
        }
    }

    public static Info process(Node X) {
        // 有的空树不好处理，就返回null，交给上游处理
        if (X == null) {
            return null;
        }
        Info leftInfo = process(X.left);
        Info rightInfo = process(X.right);
        int max = X.value;
        int min = X.value;
        if (leftInfo != null) {
            max = Math.max(leftInfo.max, max);
            min = Math.min(leftInfo.min, min);
        }
        if (rightInfo != null) {
            max = Math.max(rightInfo.max, max);
            min = Math.min(rightInfo.min, min);
        }
        boolean isBST = true;
        if (leftInfo != null && !leftInfo.isBST) { // 左树必须是BST
            isBST = false;
        }
        if (rightInfo != null && !rightInfo.isBST) { // 右树必须是BST
            isBST = false;
        }

        // 由于是上游处理空，要尤其小心！！！
        if (leftInfo != null && leftInfo.max >= X.value) { // 左树最大值不能比我大
            isBST = false;
        }
        if (rightInfo != null && rightInfo.min <= X.value) { // 右树最小值不能比我小
            isBST = false;
        }

        return new Info(isBST, max, min);
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
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isBST1(head) != isBST2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
