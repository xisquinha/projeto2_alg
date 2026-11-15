public class Main {
  public static void main(String[] args) {
    int[] a = { 7, 2, 9, 4, 1, 8, 5 };
    int k = 3;
    QuickSelect qs = new QuickSelect();
    MedianOfMedians mm = new MedianOfMedians();

    int res1 = qs.startQuickSelectAlgorithm(a, k);
    int res2 = mm.startMedianOfMedians(a, k);

    System.out.print(res2);

  }
}