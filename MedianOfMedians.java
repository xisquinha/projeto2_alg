import java.util.Arrays;

/**
 * This class implements the algorithm Median of Medians, which is an
 * deterministic selection algorithm.
 * Its principal goal is to find the k^th smallest element on a list.
 * Avoids the worst case of the QuickSelect algorithm, O(n^2), or the cost of
 * the complext sort, O(n*log(n)).
 * 
 * MedianOfMedians
 */
public class MedianOfMedians {

  QuickSelect qs;

  public MedianOfMedians() {
    this.qs = new QuickSelect();
  }

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
   * @return
   */
  private int runMedianOfMedians(int[] arr, int p, int r, int i) {
    // if start index equals end index it means that there's only one element left
    if (p == r) {
      return arr[p];
    }

    int pivotInd = medianOfMedians(arr, p, r);
    qs.swap(arr, pivotInd, r); // swap function expects the pivot at r
    int q = qs.getPartition(arr, p, r); // final index of pivot

    int k = q - p + 1; // the rank of the pivot in arr[p..r]

    if (i == k) { // the pivot value is the answer
      return arr[q];
    } else if (i < k) {
      return runMedianOfMedians(arr, p, q - 1, i);
    } else {
      return runMedianOfMedians(arr, q + 1, r, i - k);
    }
  }

  /**
   * Choose the pivot by finding the median of medians that are groups of 5 (the
   * last one can have less elements).
   * 
   * First the arr[p..r] is splitted into groups of 5, then each group is sorted
   * and its medians are taken.
   * Finally from the group with all the medians, it is selected the median of
   * medians.
   * 
   * @param arr
   * @param p
   * @param r
   * @return the index of the median of the medians
   */
  private int medianOfMedians(int[] arr, int p, int r) {
    int n = r - p + 1;
    // if it's only one group
    if (n <= 5) {
      Arrays.sort(arr, p, r + 1);
      return p + (n - 1) / 2; // index of the median
    }

    int numGroups = 0;
    for (int g = p; g <= r; g += 5) {
      int end = Math.min(g + 4, r); // for the last group that can be < 5
      Arrays.sort(arr, g, end + 1);

      int medianInd = g + (end - g) / 2;
      // put all the medians at the beginning of the array
      qs.swap(arr, p + numGroups, medianInd);
      numGroups++;
    }

    // median of the medians, which is in a[p .. p+numGroups-1]
    int midOfMid = (numGroups + 1) / 2;
    runMedianOfMedians(arr, p, p + numGroups - 1, midOfMid);
    return p + midOfMid - 1;
  }
}
