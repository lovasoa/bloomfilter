import java.util.BitSet;
import java.util.Random;

class BloomFilter {
  private BitSet hashes;
  private int k;

  public BloomFilter(int k) {
    this.k = k;
    this.hashes = new BitSet();
  }

  /**
  * Add an element to the container
  **/
  public void add(Object o) {
    Random prng = new Random(o.hashCode());
    for (int i=0; i<k; i++)
      hashes.set(prng.nextInt());
  }

  /** 
  * Returns true if the element is in the container
  * May return true or false if the element is not in the container
  **/
  public boolean contains(Object o) {
    Random prng = new Random(o.hashCode());
    for (int i=0; i<k; i++)
      if (!hashes.get(prng.nextInt()))
        return false;
    return true;
  }
}