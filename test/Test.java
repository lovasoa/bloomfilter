import java.util.*;
import java.lang.management.*;

public class Test {
  static int elements =  1_000_000;
  static int bitsize  = 10_000_000;
  static BloomFilter filter;
  static Random prng;
  static ThreadMXBean bean;

  static void testCorrectness() {
    // initialization
    System.out.println("Testing correctness.\n"+
        "Creating a filter, a set, and filling them...");
    filter.clear();
    Set<Integer> inside = new HashSet<>((int)(bitsize / 0.75)); 
    while(inside.size() < elements) {
      int v = prng.nextInt();
      inside.add(v);
      filter.add(v);
    }

    // testing
    int found = 0, total = 0;
    while (total < elements) {
      int v = prng.nextInt();
      if (inside.contains(v)) continue;
      total++;
      found += filter.contains(v) ? 1 : 0;

      if (total % 1000 == 0 || total == elements) {
        System.out.format(
            "\rElements incorrectly found to be inside: %8d/%-8d (%3.2f%%)",
            found, total,
            ((float)100*found/total)
        );
      }
    }
    System.out.println("\ndone.\n");
  }

  static void testInsertion() {
    System.out.println("Testing insertion speed...");

    filter.clear();
    long start = bean.getCurrentThreadCpuTime();
    for(int i=0; i<elements; i++) filter.add(prng.nextInt());
    long end = bean.getCurrentThreadCpuTime();
    long time = end - start;

    System.out.format(
        "Inserted %d elements in %d ns.\n" +
        "Insertion speed: %g elements/second\n\n",
        elements,
        time,
        elements/(time*1e-9)
    );
  }

  static void testQuery() {
    System.out.println("Testing query speed...");

    filter.clear();
    for(int i=0; i<elements; i++) filter.add(prng.nextInt());

    boolean xor = true; // Make sure our result isn’t optimized out
    long start = bean.getCurrentThreadCpuTime();
    for(int i=0; i<elements; i++) xor ^= filter.contains(prng.nextInt());
    long end = bean.getCurrentThreadCpuTime();
    long time = end - start;

    System.out.format(
        "Queried %d elements in %d ns.\n" +
        "Query speed: %g elements/second\n\n",
        elements,
        time,
        elements/(time*1e-9)
    );
  }

  public static void main(String[] args) {
    if (args.length >= 1) elements = Integer.parseInt(args[0]);
    if (args.length >= 2) bitsize = Integer.parseInt(args[1]);
    System.out.format(
        "Testing a bloom filter containing n=%d elements" +
        " in a bit array of m=%d bits (=%.1fMib) \n\n",
        elements, bitsize, ((float)bitsize/(1024*1024*8))
    );
    bean = ManagementFactory.getThreadMXBean();
    prng = new Random();
    prng.setSeed(0);
    filter = new BloomFilter(elements, bitsize);

    testCorrectness();
    
    testInsertion();
    
    testQuery();

  }
}
