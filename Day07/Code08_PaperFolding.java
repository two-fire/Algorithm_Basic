/**
 * 打印所有折痕
 * 上方都是凹折痕，下方都是凸折痕
 *                 1凹
 *             2凹      2凸
 *           3凹  3凸 3凹  3凸
 *        中序遍历
 *        左边头节点均为凹，右边头节点均为凸
 * 额外空间复杂度O(N)
 */
public class Code08_PaperFolding {
    public static void printAllFolds(int N) {
        // 头节点出发，在第一层，共N层。头节点为凹节点
        printProcess(1, N, true);
    }

    /**
     * 递归过程，来到某一个节点
     * @param i 当前来到的点在第几层
     * @param N 最多的层数（固定参数）
     * @param down 此时的点是否是凹折痕的点。如果是为true
     */
    private static void printProcess(int i, int N, boolean down) {
        if (i > N) {
            return;
        }
        printProcess(i+1, N, true);
        System.out.println(down ? "凹" : "凸");
        printProcess(i+1, N, true);;
    }

    public static void main(String[] args) {
        int N = 3;
        printAllFolds(N);
    }
}
