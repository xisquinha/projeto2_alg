public class Main {
  public static void main(String[] args) {
    int[] a = { 7, 2, 9, 4, 1, 8, 5 };
    int k = 2;
    QuickSelect qs = new QuickSelect(a, k);

    int res = qs.startQuickSelectAlgorithm();

    System.out.print(res);

  }
}