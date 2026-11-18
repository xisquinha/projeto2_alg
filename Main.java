import java.util.List;

public class Main {
  public static void main(String[] args) {
    int[] a = { 7, 2, 9, 4, 1, 8, 5 };
    int[] b = { 3, 1, 5, 2, 4, 8, 6, 10, 7, 9, 12, 11, 13 };
    int[] d = { 8, 9, 10, 11, 12, 28, 29, 30, 31, 32, 18, 19, 20, 21, 22, 39, 40, 41 };
    int k = 5;
    QuickSelect qs = new QuickSelect();
    MedianOfMedians mm = new MedianOfMedians();

    // int res1 = qs.startQuickSelectAlgorithm(a, k);
    // int res2 = mm.startMedianOfMedians(a, k);

    List<TestResult> results = TestRunner.runTests();

    for (TestResult result : results) {
      if (result.k == 10) {
        System.out.println(result.toString());
      }
    }

    // System.out.println(res1);
    // System.out.println(res2);

  }
}