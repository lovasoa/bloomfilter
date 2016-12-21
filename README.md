# BloomFilter

Simplistic implementation of a fixed-size container that can contain any number of elements.
 
 See the [Wikipedia page for Bloom filters](https://en.wikipedia.org/wiki/Bloom_filter).

## Implementation details

#### Hashes
It doesn't use any fancy hash function. It uses `object.hashCode()` instead. You can override your objects' `.hashCode`method if you want better hashes.

#### No allocation
It doesn't do any allocation when adding new elements or checking if an element is present. It should thus be faster than other implementations.

## How to use

```java
// Create a new bloom filter optimized for containing 100 elements and using 1024 bits of memory
BloomFilter f = new BloomFilter(100, 1024);

// Add elements to the filter
// (it uses Object.hashCode() internally, so you can add objects of any type
f.add("hello");

// Check if an element is in the filter
f.contains("hello"); // true
f.contains("hello, world!"); // false
```
