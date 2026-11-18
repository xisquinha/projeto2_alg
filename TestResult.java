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
    return arrType + " with n = " + size + ", k = " + k + ", " + algorithm + ": result=" + result + " time="
        + time / 1000000.0;
  }
}
