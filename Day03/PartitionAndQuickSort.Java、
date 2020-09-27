
public class PartitionAndQuickSort {

    public static int partiton(int[] arr, int L, int R) {  // O(N)
        if (L > R) {
            return -1;
        }
        if (L == R) {
            return L;
        }
        int lessEqual = L - 1; // < 区右边界
        int index = L;
        while (index < R) {
            if (arr[index] <= arr[R]) {
                swap(arr, index, ++lessEqual);
            }
            index++;

        }
        swap(arr, R, ++lessEqual);
        return lessEqual;
    }

    // arr[L...R] 上玩荷兰国旗的划分，一律以arr[R]作为划分值
    // < arr[R]  ==arr[R]  > arr[R]
    // 返回等于区域左右边界值
    public static int[] netherlandsFlag(int[] arr, int L, int R) {
        if (L > R) {
            return new int[] { -1, -1 };
        }
        if (L == R) {
            return new int[] { L, R };
        }
        int more = R;     // < 区右边界
        int less = L - 1; // > 区左边界
        int index = L;
        while (index < more) {
            if (arr[index] < arr[R]) {
                swap(arr, index++, ++less);
            } else if (arr[index] == arr[R]) {
                index++;
            } else {
                swap(arr, index, --more);
            }
        }
        // L...less   less+1...more-1  more...R
        // L...less   less+1...........more  more...R
        swap(arr, more, R);
        return new int[]{ less + 1, more };
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 快排1.0  O(N^2)
    public static void quickSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process1(arr, 0, arr.length - 1);
    }

    public static void process1(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        // L... R  partiton arr[R]  [<=arr[R]  arr[R]   >arr[R]]
        int M = partiton(arr, L, R);
        process1(arr, L, M - 1);
        process1(arr, M + 1, R);
    }

    // 快排2.0  O(N^2)
    public static void quickSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process2(arr, 0, arr.length - 1);
    }

    public static void process2(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        int[] equalArea = netherlandsFlag(arr, L, R);
        process2(arr, L, equalArea[0] - 1);
        process2(arr, equalArea[1] + 1, R);
    }

    // 快排3.0  O(NlogN)
    // 额外空间复杂度O(N)
    public static void quickSort3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process3(arr, 0, arr.length - 1);
    }

    public static void process3(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        // 划分值越考中间，性能越好
        // 随机行为 最差情况是概率事件 所有情况都考虑，每种占1/N => O(NlogN)
        swap(arr, L + (int) (Math.random() * (R - L + 1)), R);
        int[] equalArea = netherlandsFlag(arr, L, R);
        process2(arr, L, equalArea[0] - 1);
        process2(arr, equalArea[1] + 1, R);
    }

    // test
    public static void main(String[] args) {
        int[] arr = new int[] {4,3,5,1,0};
        //quickSort1(arr);
        //quickSort2(arr);
        quickSort3(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
