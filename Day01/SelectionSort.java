// 选择排序 O(N^2)
// 浪费比较行为导致O(N^2)
public class SelectionSort {
    public static void selectionSort(int[] arr) {
        if(arr == null || arr.length < 2) {
            return;
        }
        // 0 ~ N-1
        // 1 ~ N-1
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) { // 找最小值
                minIndex = arr[j] < arr[minIndex] ? j : minIndex;
            }
            swap(arr, i, minIndex);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }
}
