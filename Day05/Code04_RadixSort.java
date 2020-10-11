import java.util.Arrays;
/**
 * 排序算法的稳定性
 *      同样大小的数，排序后能不改变原来的顺序，稳定
 *      基础数据按值传递 稳定性无用
 *      引用传递  稳定性可能有用
 *          e.g 先按年龄排序再按班级排序 如果稳定，班级内的学生就是按年龄从小到大排序的
 *      1. 选择排序 不稳定  0~N-1 找一个最小值换到第一个…
 *      2. 冒泡排序 可稳定  0，1位置，谁大谁往后走； 1，2位置，谁大谁往后走… 相等不交换
 *      3. 插入排序 可稳定 从后往前看，小就往前换，相等不往前
 *      4. 归并排序 可稳定 两边相等时拷贝左边数组值稳定性不会被破坏
 *      5. 快速排序 不稳定
 *          1.0 如果当前数<划分值，<=区域下一个数和当前数做交换
 *          荷兰国旗问题也都做不到
 *      6. 堆排序  不稳定
 *      7. 计数排序，基数排序 不稳定
 *
 * 三种时间复杂度均为O(N*logN)
 * 贪图稳定性： 归并
 * 贪图额外空间极度优秀： 堆排 额外空间复杂度O(1)
 * 拼绝对运行时间：随机快排 常数时间最少
 *
 * 坑：
 *  1. 归并排序额外空间复杂度可以变成O(1)，“内部缓存法” 但将不再稳定 --> 直接选堆排
 *  2. “原地归并排序” 但会让时间复杂度变为O(N^2) --> 插排
 *  3. 快排也能改为稳定的，“01 stable sort”，但会限制数据 --> 不基于比较的桶排序
 *  4. 题目：把奇数放前面偶数放后面，且稳定。要求时间复杂度O(N)，空间复杂度O(1)
 *     能做到但数据限制很大。类比经典快排，那快排为什么不改成稳定的？
 *
 * 系统排序API：
 * 1. 基础数据按值传递 稳定性无用
 *      java提供sort直接快排
 *      小数据（不够60个数）直接调插入排序 常数更优，不改变复杂度
 *
 *  快排，归并，堆排：调度优秀，但常数项是不低的。时间复杂度是O(N*logN)
 *  插入排序：常数项低，但调度不好，所以时间复杂度是O(N^2)
 *
 * 2. 引用传递  稳定性可能有用    java提供sort归并排序保持稳定
 *
 *            时间复杂度   额外空间复杂度    稳定性
 * 选择排序    O(n^2)        O(1)              无
 * 冒泡排序    O(n^2)        O(1)              有
 * 插入排序    O(n^2)        O(1)              有
 * 归并排序    O(n*logn)     O(n)              有
 * 随机快排    O(n*logn)     O(logn)           无
 * 堆排序      O(n*logn)     O(1)              无
 * -----------------------------------------------
 * 计数排序    O(n)          O(M)              有
 * 基数排序    O(n)          O(n)              有
 */

public class Code04_RadixSort {

    // only for no-negative value
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        radixSort(arr, 0, arr.length - 1, maxbits(arr));
    }

    /**
     * 找到arr中最大的数，计算它的位数
     * e.g 102 11 2   数位为 3
     * @param arr
     * @return 整个数组中最大数位
     */
    private static int maxbits(int[] arr) {
        // 找到arr中最大的数
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        // 每次除以10，计数值加一
        int res = 0;
        while (max != 0) {
            res++;
            max /= 10;
        }
        return res;
    }

    /**
     * 基数排序
     * 实际 O(N*log_10 N)  -->  数据长度限制通常认为 O(N)
     * @param arr 待排数组
     * @param L   排序的范围 [L...R]
     * @param R
     * @param digit 数位
     */
    public static void radixSort(int[] arr, int L, int R, int digit) {
        final int radix = 10; // 基底为 10
        int i = 0, j = 0;
        // 有多少个数准备多少个辅助空间
        int[] help = new int[R - L + 1];
        for (int d = 1; d <= digit; d++) { // 有多少位就进出多少次
            // 申请10个空间
            // count[i]  当前位(d位)是 i 的数字有多少个
            int[] count = new int[radix];
            for (i = L; i <= R; i++) {
                j = getDigit(arr[i], d);
                count[j]++;
            }
            // count[i] 对count[i]之前的词频个数进行叠加
            // [101, 200, 012, 011, 202, 403]
            // count[0] 个数<= 0的有1个
            // count[1] 个数<= 1的有2+1=3个
            // count[2] 个数<= 2的有2+3=5个
            // count[3] 个数<= 3的有1+5=6个
            // count[i] count[i]桶中  <= i 的值的个数
            for (i = 1; i < radix; i++) {
                count[i] += count[i - 1];
            }
            // 从右往左
            // 由于出桶顺序从左往右，同个桶先进先出
            // 403  个位<=3的有6个  403一定是6个中最后出桶的  放在help[5]
            for (i = R; i >= L; i--) {
                j = getDigit(arr[i], d);
                // help中装的就是依次从桶中倒出的东西
                help[count[j] - 1] = arr[i];
                count[j]--;
            }
            // help拷贝回arr
            for (i = L, j = 0; i <= R; i++, j++) {
                arr[i] = help[j];
            }
        }
    }

    /**
     * e.g x = 103，d = 1   返回 3
     * @param x
     * @param d
     * @return 参数x的 d位上的数值
     */
    public static int getDigit(int x, int d) {
        return ((x / (int)Math.pow(10, d - 1)) % 10);
    }

    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100000;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            radixSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        radixSort(arr);
        printArray(arr);

    }

}
