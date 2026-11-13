import java.util.Random;

// Lomuto
public class QuickSelect {

  private final Random rand = new Random(42);

  int[] arr;
  int k;

  public QuickSelect(int[] arr, int k) {
    this.arr = arr.clone();
    this.k = k;
  }

  public int startQuickSelectAlgorithm() {
    return runQuickSelectAlgorithm(arr, 0, arr.length - 1, k);
  }

  private int runQuickSelectAlgorithm(int[] arr, int p, int r, int k) {
    if (p == r) {
      return arr[p];
    }

    int q = randomizedPartition(arr, p, r);
    int rank = q - p + 1;

    if (k == rank) {
      return arr[q];
    } else if (k < rank) {
      return runQuickSelectAlgorithm(arr, p, q - 1, k);
    } else {
      return runQuickSelectAlgorithm(arr, q + 1, r, k - rank);
    }
  }

  private int randomizedPartition(int[] arr, int p, int r) {
    int i = rand.nextInt(r - p + 1) + p; // range [p, r]
    exchange(arr, r, i);
    return getPartition(arr, p, r);
  }

  private int getPartition(int[] arr, int p, int r) {

    int x = arr[r];
    int i = p - 1;

    for (int j = p; j <= r - 1; j++) {
      if (arr[j] <= x) {
        i = i + 1;
        exchange(arr, i, j);
      }
    }
    exchange(arr, i + 1, r);
    return i + 1;
  }

  private void exchange(int[] arr, int i, int j) {
    int arr_i = arr[i];
    arr[i] = arr[j];
    arr[j] = arr_i;
  }
}
