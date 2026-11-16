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
   * @return
   */
  private int runMedianOfMedians(int[] arr, int p, int r, int i) {
    // if start index equals end index it means that there's only one element left
    if (p == r) {
      return arr[p];
    }

    int pivotInd = medianOfMedians(arr, p, r);
    // swap function expects the pivot at r
    PartitionUtils.swap(arr, pivotInd, r);
    int q = PartitionUtils.partition(arr, p, r); // final index of pivot
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
   * and its median is taken and finally the median of the meadians is selected.
   * 
   * @param arr
   * @param p
   * @param r
   * @return the indec of the pivot
   */
  private int medianOfMedians(int[] arr, int p, int r) {
    int n = r - p + 1;
    // if it's only one group
    if (n <= 5) {
      insertionSort(arr, p, r);
      return p + (n - 1) / 2; // index of the median
    }

    int numGroups = 0;
    for (int g = p; g <= r; g += 5) {
      int end = Math.min(g + 4, r);
      insertionSort(arr, g, end);

      int medianInd = g + (end - g) / 2;
      PartitionUtils.swap(arr, p + numGroups, medianInd); // put medians at the front
      numGroups++;
    }

    // median of the medians, which is in a[p .. p+numGroups-1]
    int mid = (numGroups + 1) / 2;
    runMedianOfMedians(arr, p, p + numGroups - 1, mid);
    return p + mid - 1; // x was placed at this index
  }

  private void insertionSort(int[] arr, int lo, int hi) {
    for (int j = lo + 1; j <= hi; j++) {
      int key = arr[j];
      int i = j - 1;
      while (i >= lo && arr[i] > key) {
        arr[i + 1] = arr[i];
        i--;
      }
      arr[i + 1] = key;
    }
  }
}
