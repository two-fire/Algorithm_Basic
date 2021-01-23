import java.util.ArrayList;

/**
 * 给定一棵二叉树的头节点head，找到满足搜索二叉树的子树，返回个数
 *      1）x无关
 *          左树上（右树上）满足二叉搜索树的子树节点个数
 *      2）x有关
 *          左树和右树都要是二叉搜索树
 *          并且左树上最大值 < X，右树上最小值 > X
 *
 *      需要的信息： 左树 ① 最大搜索二叉树大小（即节点个数）
 *                       ② 左树整体是不是搜索二叉树
 *                       ③ 左树的最大值
 *                  右树 ① 最大搜索二叉树大小（即节点个数）
 *                       ② 右树整体是不是搜索二叉树
 *                       ③ 右树的最小值
 *
 *                       | 信息整合
 *                       V
 *                  最大搜索二叉树大小（即节点个数）
 *                  整体是不是搜索二叉树
 *                  整棵树的最大值和最小值
 *
 */
public class Code04_MaxSubBSTSize {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static int getBSTSize(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return 0;
            }
        }
        return arr.size();
    }

    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    public static int maxSubBSTSize1(Node head) {
        if (head == null) {
            return 0;
        }
        int h = getBSTSize(head);
        if (h != 0) {
            return h;
        }
        return Math.max(maxSubBSTSize1(head.left), maxSubBSTSize1(head.right));
    }

    public static int maxSubBSTSize2(Node head) {
        if (head == null) {
            return 0;
        }
        return process(head).maxSubBSTSize;
    }

    public static class Info {
        public boolean isAllBST;
        public int maxSubBSTSize;
        public int min;
        public int max;

        public Info(boolean is, int size, int min, int max) {
            isAllBST = is;
            maxSubBSTSize = size;
            this.min = min;
            this.max = max;
        }
    }

    public static Info process(Node X) {
        if (X == null) {
            return null;
        }

        Info leftInfo = process(X.left);
        Info rightInfo = process(X.right);

        /* min和max的加工 */
        int min = X.value;
        int max = X.value;
        if (leftInfo != null) {
            min = Math.min(min, leftInfo.min);
            max = Math.max(max, leftInfo.max);
        }
        if (rightInfo != null) {
            min = Math.min(min, rightInfo.min);
            max = Math.max(max, rightInfo.max);
        }

        /* 最大子搜的加工 */
        int maxSubBSTSize = 0;
        // 考虑可能性 1）x无关
        if (leftInfo != null) {
            maxSubBSTSize = leftInfo.maxSubBSTSize;
        }
        if (rightInfo != null) {
            maxSubBSTSize = Math.max(maxSubBSTSize, rightInfo.maxSubBSTSize);
        }

        // 考虑可能性 2）x有关

        // 整体是不是搜索二叉树的加工
        boolean isAllBST = false;

        if (
                // 左、右树整体需要是搜索二叉树
                (leftInfo == null ? true : leftInfo.isAllBST)
                &&
                (rightInfo == null ? true : rightInfo.isAllBST)
                &&
                // 左树最大值 < x
                (leftInfo == null ? true : leftInfo.max < X.value)
                &&
                (rightInfo == null ? true : leftInfo.min > X.value)
        ) {
            // 求以x为头的所有节点数
            maxSubBSTSize =
                    (leftInfo == null ? 0 : leftInfo.maxSubBSTSize)
                    + (rightInfo == null ? 0 : rightInfo.maxSubBSTSize)
                    + 1;
            isAllBST = true;

        }

        return new Info(isAllBST, maxSubBSTSize, min, max);
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
            if (maxSubBSTSize1(head) != maxSubBSTSize2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
