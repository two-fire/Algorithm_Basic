/* 一个数组上，将每个数前面所有比它小的数求和
 * 即求每个数后面所有比它大的数的和
 *      在纠结前面多少数比我大（或后面多少个数比我小）时候可以使用归并排序
 */

public class SmallSum {
    public static int smallSum (int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }
    // arr[L... R]既要排好序，也要求小和返回
    public static int process(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }
        // l < r
        int mid = l + ((r - l) >> 1);
        return process(arr, l, mid)
                + process(arr, mid + 1, r)
                + merge(arr, l, mid, r);
    }

    // 所有merge时，产生的小和累加
    public static int merge(int[] arr, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int p1 = l;
        int p2 = mid + 1;
        int i = 0;
        int res = 0;
        while (p1 <= mid && p2 <= r) {
            res += arr[p1] < arr[p2] ? (r - p2 + 1) * arr[p1] : 0;
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        // 左边剩下的全部链接到help数组后面
        while (p1 <= mid) {
            help[i++] = arr[p1++];
        }
        // 右边剩下的全部链接到help数组后面
        while (p2 <= r) {
            help[i++] = arr[p2++];
        }
        for (i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
        return res;
    }
    // test
    public static void main(String[] args) {
        int[] arr = new int[] {4,3,5,1,0};
        System.out.println("最小和为"+smallSum (arr));
    }
}
