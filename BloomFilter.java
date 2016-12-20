class BloomFilter {
  private int hashes = 0;

  /**
  * Add an element to the container
  **/
  public void add(Object o) {
    hashes |= o.hashCode();
  }

  /** 
  * Returns true if the element is in the container
  * May return true or false if the element is not in the container
  **/
  public boolean contains(Object o) {
    int h = o.hashCode();
    return ((h & hashes) == h);
  }
}