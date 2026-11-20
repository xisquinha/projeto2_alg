import java.util.Random;

/**
 * This class implements the algorithm Quick Select, which is a random selection
 * algorithm.
 * Finds the k^th smallest element of an unsorted array without sorting it, by
 * partitioning (Lomuto) and recursing only into the side that contains the k^th
 * element.
 * 
 * Random pivot: expected O(n).
 * Fixed pivot (arr[r]): O(n^2) on sorted or inverse sorted arrays, used to show
 * the worst case.
 * Many duplicates also degrade it to O(n^2) with Lomuto.
 */
public class QuickSelect {

  private final Random rand = new Random(42);

  /**
   * Finds the k^th smallest element of arr using a random pivot.
   * Expected time O(n).
   * 
   * @param arr: array to search
   * @param k:   rank to look for (1 = minimum)
   * @return the k^th smallest element of arr
   */
  public int startQuickSelectAlgorithm(int[] arr, int k) {
    return runQuickSelectAlgorithm(arr.clone(), 0, arr.length - 1, k);
  }

  /**
   * Finds the k^th smallest element of arr using a fixed pivot (always the last
   * element of the subarray). Worst case O(n^2) on sorted or inverse sorted
   * arrays.
   * (This method is meant for comparison and demonstration)
   *
   * @param arr: array to search
   * @param k:   rank to look for (1 = minimum)
   * @return the k^th smallest element of arr
   */
  public int startQuickSelectAlgorithmFixedPivot(int[] arr, int k) {
    return runQuickSelectWithFixedPivot(arr.clone(), 0, arr.length - 1, k);
  }

  /**
   * 
   * @param arr: array that we are searching
   * @param p:   start index of the subarray arr[p..r]
   * @param r:   end index of the subarray arr[p..r]
   * @param i:   rank being looked for (i^th smallest), relative to the subarray
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
      // search through the subarray with the elements <= than the pivot (left part)
      return runQuickSelectAlgorithm(arr, p, q - 1, i);
    } else {
      // search through the subarray with the elements > than the pivot (right part)
      return runQuickSelectAlgorithm(arr, q + 1, r, i - k);
    }
  }

  /**
   * Same as runQuickSelectAlgorithm, but the pivot is always arr[r] instead of a
   * random element. On sorted or inverse sorted arrays each partition removes
   * only one element, so it takes O(n^2) time.
   *
   * @param arr: array that we are searching
   * @param p:   start index of the subarray arr[p..r]
   * @param r:   end index of the subarray arr[p..r]
   * @param i:   rank being looked for (i^th smallest), relative to the subarray
   * @return the i^th smallest element of arr[p..r]
   */
  private int runQuickSelectWithFixedPivot(int[] arr, int p, int r, int i) {
    if (p == r) {
      return arr[p];
    }

    int q = PartitionUtils.partition(arr, p, r); // pivot = arr[r]
    int k = q - p + 1; // how many elements are <= the pivot, plus the pivot itself

    if (i == k) { // the pivot value is the answer
      return arr[q];
    } else if (i < k) {
      // search through the subarray with the elements <= than the pivot (left part)
      return runQuickSelectWithFixedPivot(arr, p, q - 1, i);
    } else {
      // search through the subarray with the elements > than the pivot (right part)
      return runQuickSelectWithFixedPivot(arr, q + 1, r, i - k);
    }
  }

  /**
   * Picks a random pivot and rearranges the subarray so that everything in the
   * subarray arr[p..q-1] is <= the pivot, the pivot is in q and everything in the
   * subarray arr[q+1..r] is > the pivot.
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
