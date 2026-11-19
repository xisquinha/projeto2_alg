public class TestResult {
  String arrType;
  String algorithm;
  int size;
  int k;
  int result;
  long time;

  public TestResult(String arrType, String algorithm, int size, int k, int result, long time) {

    this.arrType = arrType;
    this.algorithm = algorithm;
    this.size = size;
    this.k = k;
    this.result = result;
    this.time = time;
  }

  @Override
  public String toString() {
    return String.format(
            "%-20s | n = %7d | k = %7d | %-17s | result = %8d | time = %9.4f ms",
            arrType,
            size,
            k,
            algorithm,
            result,
            time / 1_000_000.0
    );
  }
}
