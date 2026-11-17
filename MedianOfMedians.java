import java.util.Arrays;

/**
 * This class implements the algorithm Median of Medians, which is a
 * deterministic selection algorithm.
 * Its principal goal is to find the k^th smallest element on a list.
 * Avoids the worst case of the QuickSelect algorithm, O(n^2), or the cost of
 * the complex sort, O(n*log(n)).
 * 
 * MedianOfMedians
 */
public class MedianOfMedians {

  public int startMedianOfMedians(int[] arr, int k) {
    return runMedianOfMedians(arr.clone(), 0, arr.length - 1, k);
  }

  /**
   * Finds the i^th smallest element inside the subarray arr[p..r]
   * 
   * @param arr: array being searched
   * @param p:   start index of arr array from where we search
   * @param r:   end index of arr array from where we search
   * @param i:   is the rank that it's being looked for (i^th position), relative
   *             to the subarray
   * @return the i^th smallest element of arr[p..r]
   */
  private int runMedianOfMedians(int[] arr, int p, int r, int i) {
    // if start index equals end index it means that there's only one element left
    if (p == r) {
      return arr[p];
    }

    // to find the pivot deterministically
    int pivotInd = medianOfMedians(arr, p, r);

    // the swap function expects the pivot at r
    PartitionUtils.swap(arr, pivotInd, r);

    int q = PartitionUtils.partition(arr, p, r); // final index of pivot
    int k = q - p + 1; // the rank of the pivot in arr[p..r]

    if (i == k) { // the pivot value is the answer
      return arr[q];
    } else if (i < k) {
      // search through the subarray with the elements <= than the pivot (left part)
      return runMedianOfMedians(arr, p, q - 1, i);
    } else {
      // search through the subarray with the elements > than the pivot (right part)
      return runMedianOfMedians(arr, q + 1, r, i - k);
    }
  }

  /**
   * Choose the pivot by finding the median of medians that are groups of 5 (the
   * last one can have less elements).
   * 
   * First the arr[p..r] is split into groups of 5, then each group is sorted
   * and its medians are taken.
   * Finally from the group with all the medians, it is selected the median of
   * medians.
   * 
   * @param arr: array being searched
   * @param p:   start index of the subarray arr[p..r]
   * @param r:   end index of the subarray arr[p..r]
   * @return the index (inside arr[p..r]) of the pivot, the median of the medians
   */
  private int medianOfMedians(int[] arr, int p, int r) {
    // number of elements in arr[p..r]
    int n = r - p + 1;

    // if it's only one group
    if (n <= 5) {
      Arrays.sort(arr, p, r + 1);
      return p + (n - 1) / 2; // index of the (lower) median
    }

    int numGroups = 0;
    for (int g = p; g <= r; g += 5) {

      int end = g + 4;
      // the last group can be less than 5
      if (end > r) {
        end = r;
      }

      Arrays.sort(arr, g, end + 1); // last index is exclusive
      int medianInd = g + (end - g) / 2; // index of the (lower) median

      // put all the medians at the beginning of the subarray
      PartitionUtils.swap(arr, p + numGroups, medianInd);
      numGroups++;
    }

    // the rank of the median of the medians
    int midOfMid = (numGroups + 1) / 2;
    // rearranges the subarray [p..p+numGroups-1] (where we placed the medians), so
    // the median of the medians will be at the midOfMid position. This is because
    // we have gathered the medians but they are not sorted
    runMedianOfMedians(arr, p, p + numGroups - 1, midOfMid);
    return p + midOfMid - 1;
  }
}