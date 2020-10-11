import java.util.Arrays;

/**
 * 桶排序（思想） 不基于比较的排序  桶-->用容器
 *      改写代价大
 *      数据状况很窄  或者有严格的限定  时间复杂度O(N) 额外空间复杂度O(M)
 *      计数排序 CountSort 一般来说，样本要求整数，且范围较窄
 *          员工年龄排序  数组[0……200] 下标代表年龄
 *      基数排序 RadixSort  十进制非负整数
 *          1. 根据最大长度的正数高位零补齐
 *          2. 从左到右根据个位数字进桶（队列）
 *             从左到右倒出，同个桶内先进的先倒出
 *          3. 根据十位数字进桶
 *             倒出
 *          4. 根据百位数字进桶
 *             倒出
 *          ……
 *
 * 除非特殊说明，排序都按照基于比较的排序进行估算
 */

public class Code03_CountSort {

    // 计数排序 only for 0~200 value
    public static void countSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 找到最大年龄
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        // 下标代表年龄 0 ~ max
        int[] bucket = new int[max+1];
        for (int i = 0; i < arr.length; i++) {
            bucket[arr[i]]++;
        }
        int i = 0;
        for (int j = 0; j < arr.length; j++) {
            while (bucket[j]-- > 0) {
                arr[i++] = j;
            }
        }
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
        int maxValue = 150;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            countSort(arr1);
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
        countSort(arr);
        printArray(arr);

    }
}
