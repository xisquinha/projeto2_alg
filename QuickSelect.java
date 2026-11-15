import java.util.Random;

// Lomuto
public class QuickSelect {

  private final Random rand = new Random(42);

  public int startQuickSelectAlgorithm(int[] arr, int k) {
    return runQuickSelectAlgorithm(arr.clone(), 0, arr.length - 1, k);
  }

  /**
   * 
   * @param arr: array that we are searching
   * @param p:   start index of arr array from where we search
   * @param r:   end index of arr array from where we search
   * @param i:   is the rank that it's being looked for (i^th position), relative
   *             to the subarray
   * @return
   */
  private int runQuickSelectAlgorithm(int[] arr, int p, int r, int i) {
    // if start index equals end index it means that there's only one element left
    if (p == r) {
      return arr[p];
    }

    // final index of the pivot after partitioning
    int q = randomizedPartition(arr, p, r);
    int k = q - p + 1; // how many elements are <= the pivot, plus the pivot itself

    if (i == k) { // the pivot value is the answer
      return arr[q];
    } else if (i < k) {
      // search through the subarray with the elements < than the pivot (left part)
      return runQuickSelectAlgorithm(arr, p, q - 1, i);
    } else {
      // search through the subarray with the elements > than the pivot (right part)
      return runQuickSelectAlgorithm(arr, q + 1, r, i - k);
    }
  }

  /**
   * Picks a random pivot and rearranges the subarray so that everything in the
   * subarray [p..q-1] is <= the pivot, the pivot is in q and everything in the
   * subarray [q+1..r] is > the pivot.
   * 
   * @param arr
   * @param p
   * @param r
   * @return
   */
  private int randomizedPartition(int[] arr, int p, int r) {
    int i = rand.nextInt(r - p + 1) + p; // range [p, r]
    swap(arr, r, i);
    return getPartition(arr, p, r);
  }

  /**
   * iterate the array and put all the elements <= than the pivot at the left of
   * the array, > than the pivot at the right and the pivot at the middle of the
   * two groups
   * 
   * @param arr: array that will be iterated
   * @param p:   start index
   * @param r:   end index
   * @return index of pivot
   */
  public int getPartition(int[] arr, int p, int r) {

    int x = arr[r]; // pivot
    int i = p - 1;

    // elements <= pivot at left, > pivot at right and pivot at the end
    for (int j = p; j <= r - 1; j++) {
      if (arr[j] <= x) {
        i = i + 1;

        if (i != j)
          swap(arr, i, j);
      }
    }

    // pivot at the middle
    swap(arr, i + 1, r);
    return i + 1;
  }

  // swap places of the elements at the indices i and j of the arr array
  public void swap(int[] arr, int i, int j) {
    int arr_i = arr[i];
    arr[i] = arr[j];
    arr[j] = arr_i;
  }
}
