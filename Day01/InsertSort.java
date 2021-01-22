// 插入排序
public class InsertSort {
    public static void selectionSort(int[] arr) {
        // 0
        // arr[1] 往前比较插入
        // arr[2] 往前比较插入
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
                swap(arr, i, j+1);
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }
}
