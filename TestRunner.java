import java.util.*;

public class TestRunner {
  final static int[] instancesSize = {
          1, 2, 3, 5, 10,
          100, 123, 500, 501, 502, 503, 504, 1000,
          5000, 10000, 50000, 100000, 500000, 1000000
  };

  final static Random rand = new Random(42);
  static List<TestResult> testResults;

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

  private static void runAlgorithms(QuickSelect qs, MedianOfMedians mm, int[] arr, int k, String arrType) {

    // System.out.println("========= Instance: " + arrType + ", with n = " +
    // arr.length + ", with k =" + k + " =========");

    long startTime = System.nanoTime();
    int result = qs.startQuickSelectAlgorithm(arr, k);
    long elapsed = System.nanoTime() - startTime;

    testResults.add(new TestResult(arrType, "QuickSelect", arr.length, k, result, elapsed));

    // System.out.printf("QuickSelect: Result = %2d, Execution Time: %.3f ms%n",
    // result, elapsed / 1000000.0);

    startTime = System.nanoTime();
    result = mm.startMedianOfMedians(arr, k);
    elapsed = System.nanoTime() - startTime;

    // System.out.printf("Median of Medians: Result = %2d, Execution Time: %.3f
    // ms%n", result, elapsed / 1000000.0);

    testResults.add(new TestResult(arrType, "Median of Medians", arr.length, k, result, elapsed));

  }

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

  // pre: n <= bound
  // generates an integer array with random n distinct elements
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

  public static void reverse(int[] arr) {
    int i = 0;
    int j = arr.length - 1;

    while (i < j) {
      PartitionUtils.swap(arr, i, j);
      i++;
      j--;
    }
  }

}
