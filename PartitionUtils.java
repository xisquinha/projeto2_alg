public class PartitionUtils {

  /**
   * Iterate the array and put all the elements <= than the pivot at the left of
   * the array, > than the pivot at the right and the pivot at the middle of the
   * two groups
   *
   * @param arr: array that will be iterated
   * @param p:   start index
   * @param r:   end index
   * @return index of pivot
   */
  public static int partition(int[] arr, int p, int r) {
    int x = arr[r]; // pivô
    int i = p - 1;

    // elements <= pivot at left, > pivot at right and pivot at the end
    for (int j = p; j <= r - 1; j++) {
      if (arr[j] <= x) {
        i++;
        if (i != j)
          swap(arr, i, j);

      }
    }

    swap(arr, i + 1, r);
    return i + 1;
  }

  // swap places of the elements at the indices i and j of the arr array
  public static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }
}
