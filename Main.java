import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class Main {
  public static void main(String[] args) {

    System.out.println("Running experimental tests for QuickSelect and Median-of-Medians...");
    List<TestResult> results = TestRunner.runTests();
    System.out.println("Tests finished. Total records: " + results.size());

    runCommandInterpreter(results);
  }

  /**
   * Starts the interactive command loop for inspecting test results and
   * running extra demos.
   *
   * @param results list of benchmark results from the main test suite
   */
  private static void runCommandInterpreter(List<TestResult> results) {
    Scanner scanner = new Scanner(System.in);
    printHelp();

    boolean running = true;
    while (running) {
      System.out.print("\ncommand> ");
      if (!scanner.hasNextLine()) {
        break;
      }

      String input = scanner.nextLine().trim();
      if (input.isEmpty()) {
        continue;
      }

      String[] parts = input.split("=", 2);
      String cmd = parts[0].trim().toLowerCase();
      String arg = parts.length > 1 ? parts[1].trim() : "";

      switch (cmd) {
        case "exit", "quit" -> {
          System.out.println("Exiting...");
          running = false;
        }
        case "help" -> printHelp();
        case "all" -> printMatching(results, r -> true);
        case "k" -> {
          Integer kVal = parseIntSafe(arg);
          if (kVal != null) {
            printMatching(results, r -> r.k == kVal);
          } else {
            System.out.println("Invalid value for k.");
          }
        }
        case "n", "size" -> {
          Integer sizeVal = parseIntSafe(arg);
          if (sizeVal != null) {
            printMatching(results, r -> r.size == sizeVal);
          } else {
            System.out.println("Invalid value for size.");
          }
        }
        case "type" -> printMatching(results, r -> r.arrType.equalsIgnoreCase(arg));
        case "alg" -> printMatching(results, r -> r.algorithm.toLowerCase().contains(arg.toLowerCase()));
        case "worstcase", "worst" -> runAndPrintWorstCaseDemo();
        default -> System.out.println("Unknown command. Type 'help' to see available commands.");
      }
    }

    scanner.close();
  }

  private static void printMatching(List<TestResult> results, Predicate<TestResult> filter) {
    int count = 0;
    for (TestResult r : results) {
      if (filter.test(r)) {
        System.out.println(r.toString());
        count++;
      }
    }
    System.out.println("--- Shown: " + count + " result(s) ---");
  }

  private static Integer parseIntSafe(String s) {
    try {
      return Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  /**
   * Runs the worst-case demonstration comparing Random vs Fixed Pivot QuickSelect
   * on sorted arrays and prints the results directly to stdout.
   */
  private static void runAndPrintWorstCaseDemo() {
    System.out.println("\n=== Running Worst-Case Demonstration (O(n) vs O(n^2)) ===");
    List<TestResult> worstCaseResults = TestRunner.runWorstCaseDemo();

    for (int i = 0; i + 1 < worstCaseResults.size(); i += 2) {
      TestResult randomRes = worstCaseResults.get(i);
      TestResult fixedRes = worstCaseResults.get(i + 1);

      System.out.printf("n = %5d | QuickSelect Random: %7.3f ms | QuickSelect Fixed: %7.3f ms%n",
              randomRes.size,
              randomRes.time / 1000000.0,
              fixedRes.time / 1000000.0);
    }
  }

  /**
   * Prints the list of available interactive commands to standard output.
   */
  private static void printHelp() {
    System.out.println("\nAvailable commands:");
    System.out.println("  all                  -> print all test results");
    System.out.println("  k=<number>           -> filter by k (e.g., k=2)");
    System.out.println("  n=<number>           -> filter by array size (e.g., n=1000)");
    System.out.println("  type=<array_type>    -> filter by array type: Random Array | Sorted Array | Inverse Sorted Array");
    System.out.println("  alg=<algorithm_name> -> filter by algorithm: QuickSelect | Median of Medians");
    System.out.println("  worstcase            -> Run worst-case QuickSelect demo (Random vs Fixed pivot on Sorted Array)");
    System.out.println("  help                 -> show this help message");
    System.out.println("  exit                 -> quit interpreter");
  }

}