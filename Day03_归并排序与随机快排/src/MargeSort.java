// 归并排序 O(NlogN)

public class MargeSort {
    // 递归方法实现
    public static void mergeSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    // arr[L...R] 排序
    // L...R    N    T(N) = 2*T(N/2) + O(N)   O(NlogN)
    public static void process(int[] arr, int L, int R) {
        // base case
        if (L == R) {
            return;
        }
        int M = L + ((R - L) >> 1);
        // 左边排序
        process(arr, L, M);
        // 右边排序
        process(arr, M+1, R);
        // 归并 T(N) = O(N)
        merge(arr, L, M, R);
    }

    // 两段数组比较合并
    public static void merge(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = M + 1;
        // p1和p2都不越界的时候，谁小拷贝谁到help。相等默认左边
        while (p1 <= M && p2 <= R) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        // 要么p1越界，要么p2越界，跳出
        // p1没越界，将p1剩下的部分拷入help
        while (p1 <= M) {
            help[i++] = arr[p1++];
        }
        // p2没越界，将p2剩下的部分拷入help
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }
        //把help内容返回arr
        for(i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
    }

    // 非递归方法实现
    // 每一轮进行多次merge，logN  merge()的时间复杂度O(N)  O(NlogN)
    public static void mergeSort2(int[] arr) {
        // base case
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        // 当前有序的左组长度 右部分可以小于mergeSize 左组一定有序
        int mergeSize = 1;
        // mergeSize：1，2，4，8…直到 >= N
        while (mergeSize < N) {
            // L...M 左组
            int L = 0;
            while (L < N) {
                int M = L + mergeSize - 1;
                // 如果最后剩下的小于等于mergeSize，最后一组肯定有序
                if(M >= N) {
                    break;
                }
                // M+1...R 右组
                // 理想情况下右组有mergeSize个 也可能凑不齐
                int R = Math.min(M + mergeSize, N - 1);
                // 归并
                merge(arr, L, M, R);
                L = R + 1;
            }
            // 为了防止之后乘二可能出现的溢出
            if (mergeSize > N / 2) {
                break;
            }
            mergeSize <<= 1; // mergeSize*2
        }
    }

    // test
    public static void main(String[] args) {
        int[] arr = new int[] {4,3,5,1,0};
        //mergeSort1(arr);
        mergeSort2(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }

//        for(int i : arr) {
//            System.out.println(i);
//        }

    }
}
