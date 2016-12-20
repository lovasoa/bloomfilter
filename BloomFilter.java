class BloomFilter {
  private int hashes = 0;
  public void add(Object o) {
    hashes |= o.hashCode();
  }
  public boolean contains(Object o) {
    int h = o.hashCode();
    return ((h & hashes) == h);
  }
}