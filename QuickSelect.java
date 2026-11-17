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
   * @return the i^th smallest element of arr[p..r]
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
   * @param arr: array being partitioned
   * @param p:   start index of the subarray arr[p..r]
   * @param r:   end index of the subarray arr[p..r]
   * @return the pivot index after partitioning
   */
  private int randomizedPartition(int[] arr, int p, int r) {
    int i = rand.nextInt(r - p + 1) + p; // range [p, r]
    PartitionUtils.swap(arr, r, i);
    return PartitionUtils.partition(arr, p, r);
  }
}
