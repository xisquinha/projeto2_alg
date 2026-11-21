import java.util.*;

public class TestRunner {
  final static int[] instancesSize = {
          1, 2, 3, 5, 10,
          100, 123, 500, 501, 502, 503, 504, 1000,
          5000, 10000, 50000, 100000, 500000, 1000000
  };

  final static Random rand = new Random(42);
  static List<TestResult> testResults;

  /**
   * Executes the full benchmark suite across configured input sizes, array
   * orderings, and target ranks.
   *
   * @return list of recorded test results
   */
  public static List<TestResult> runTests() {
    QuickSelect qs = new QuickSelect();
    MedianOfMedians mm = new MedianOfMedians();

    testResults = new ArrayList<>();

    for (int size : instancesSize) {

      int[] randomArray = generateRandomArray(size);

      int[] sortedArray = randomArray.clone();
      Arrays.sort(sortedArray);

      int[] invSortedArray = sortedArray.clone();
      reverse(invSortedArray);

      List<Integer> ks = generateKVal(size);

      for (int k : ks) {
        runAlgorithms(qs, mm, randomArray, k, "Random Array");
        runAlgorithms(qs, mm, sortedArray, k, "Sorted Array");
        runAlgorithms(qs, mm, invSortedArray, k, "Inverse Sorted Array");
      }
    }

    return testResults;
  }

  /**
   * Runs QuickSelect and Median-of-Medians on a given array instance and
   * records their execution times.
   *
   * @param qs QuickSelect instance to benchmark
   * @param mm MedianOfMedians instance to benchmark
   * @param arr input array to search
   * @param k target rank (1-based)
   * @param arrType label describing the initial array ordering
   */
  private static void runAlgorithms(QuickSelect qs, MedianOfMedians mm, int[] arr, int k, String arrType) {

    long startTime = System.nanoTime();
    int result = qs.startQuickSelectAlgorithm(arr, k);
    long elapsed = System.nanoTime() - startTime;
    testResults.add(new TestResult(arrType, "QuickSelect", arr.length, k, result, elapsed));

    startTime = System.nanoTime();
    result = mm.startMedianOfMedians(arr, k);
    elapsed = System.nanoTime() - startTime;
    testResults.add(new TestResult(arrType, "Median of Medians", arr.length, k, result, elapsed));

  }

  /**
   * Builds a sorted list of rank values to test for a given array size.
   *
   * @param n size of the input array
   * @return list of target ranks to evaluate
   */
  private static List<Integer> generateKVal(int n) {
    List<Integer> ks = new ArrayList<>();
    ks.add(1);

    if (n > 2) {
      ks.add(n);
    }

    if (n > 1) {
      ks.add(2);

      if (n > 3) {
        ks.add(n - 1);
      }
    }

    int lowerMed = (n + 1) / 2;
    int highMed = n / 2 + 1;
    if (!ks.contains(lowerMed)) {
      ks.add(lowerMed);
    }
    if (!ks.contains(highMed)) {
      ks.add(highMed);
    }

    if (n >= 100) {
      // add equally spaced k's
      int step = (int) (0.1 * n);
      for (int i = step; i <= n - step; i = i + step) {
        if (!ks.contains(i)) {
          ks.add(i);
        }
      }
    }

    Collections.sort(ks);

    return ks;
  }

  /**
   * Generates an array of distinct random integers within a fixed upper bound.
   *
   * @param n number of elements to generate
   * @pre: n <= bound
   * @return array of distinct random integers
   */
  private static int[] generateRandomArray(int n) {
    Set<Integer> set = new LinkedHashSet<>();
    int bound = 10_000_000;

    while (set.size() < n) {
      set.add(rand.nextInt(bound));
    }

    int[] arr = new int[n];
    int i = 0;
    for (int num : set) {
      arr[i++] = num;
    }

    return arr;
  }

  /**
   * Reverses the elements of the given array in place.
   *
   * @param arr array to reverse
   */
  public static void reverse(int[] arr) {
    int i = 0;
    int j = arr.length - 1;

    while (i < j) {
      PartitionUtils.swap(arr, i, j);
      i++;
      j--;
    }
  }


  /**
   * Runs a focused demonstration comparing QuickSelect with a random pivot
   * against QuickSelect with a fixed pivot ({@code arr[r]}) on sorted arrays
   * to illustrate the $O(n^2)$ worst-case degradation without running all test
   * instances.
   *
   * @return list of TestResult records for the worst-case comparison
   */
  public static List<TestResult> runWorstCaseDemo() {
    QuickSelect qs = new QuickSelect();
    List<TestResult> worstCaseResults = new ArrayList<>();

    // Moderate sizes to demonstrate an O(n^2) degradation without stack overflow
    int[] demoSizes = {100, 500, 1000, 5000, 10000, 15000};

    for (int size: demoSizes) {
      int[] sortedArray = generateRandomArray(size);
      Arrays.sort(sortedArray);

      int k = 1;

      long startTime = System.nanoTime();
      int resRandom = qs.startQuickSelectAlgorithm(sortedArray, k);
      long elapsedRandom = System.nanoTime() - startTime;
      worstCaseResults.add(
              new TestResult("Sorted Array (Random Pivot)", "QuickSelect", size, k, resRandom, elapsedRandom));

      startTime = System.nanoTime();
      int resFixed = qs.startQuickSelectAlgorithmFixedPivot(sortedArray, k);
      long elapsedFixed = System.nanoTime() - startTime;
      worstCaseResults.add(
              new TestResult("Sorted Array (Fixed Pivot)", "QuickSelect-Fixed", size, k, resFixed, elapsedFixed));
    }

    return worstCaseResults;
  }
}
