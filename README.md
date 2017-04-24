# BloomFilter

[![Build Status](https://travis-ci.org/lovasoa/bloomfilter.svg?branch=master)](https://travis-ci.org/lovasoa/bloomfilter)
[![Jitpack repository](https://jitpack.io/v/lovasoa/BloomFilter.svg)](https://jitpack.io/#lovasoa/BloomFilter)

A Bloom filter is a hashing based data structure for maintaining a set of items in limited memory, allowing false positives but no false negatives.

This repository contains a simple but performant implementation of bloom filters in java.

See the [Wikipedia page for Bloom filters](https://en.wikipedia.org/wiki/Bloom_filter).

## How to use
### Add the project to your dependencies
You can add this project to your dependencies very easily using *jitpack.io*. [See instructions](https://jitpack.io/#lovasoa/BloomFilter/).
### Usage example
```java
import com.github.lovasoa.bloomfilter.BloomFilter;

// Create a new bloom filter optimized for containing 100 elements and using 1024 bits of memory
BloomFilter f = new BloomFilter(100, 1024);

// Add elements to the filter
// it uses Object.hashCode() internally, so you can add objects of any type
f.add("hello");

// Check if an element is in the filter
f.contains("hello"); // true
f.contains("hello, world!"); // false
```
### Documentation
Read [the full javadoc of the **BloomFilter** class](https://lovasoa.github.io/bloomfilter/apidocs/com/github/lovasoa/bloomfilter/BloomFilter.html).

## Implementation details

#### Hashes
It doesn't use any fancy hash function. It uses `object.hashCode()` instead. You can override your objects' `.hashCode`method if you want better hashes.

#### No allocation
It doesn't do any allocation when adding new elements or checking if an element is present. It should thus be faster than many other implementations.

## Performance
A class is provided that helps making performance measurements: [`Test.java`](./src/test/java/Test.java).
It tests the implementation with a Bloom filter containing randomly generated integers.

Here are the results it gives on my laptop (*`Core i7-4600M CPU @ 2.90GHz`*) with a set of 10 million integers added to a 10 megabyte Bloom filter:
```
Testing a bloom filter containing n=10000000 elements in a bit array of m=80000000 bits (=9.5Mib) 

Testing correctness.
Creating a filter, a set, and filling them...
Elements incorrectly found to be inside:   215013/10000000 (2.15%)
done.

Testing insertion speed...
Inserted 10000000 elements in 3445388006 ns.
Insertion speed: 2.90243e+06 elements/second

Testing query speed...
Queried 10000000 elements in 1537504033 ns.
Query speed: 6.50405e+06 elements/second
```

We see that:
  * The implementation is correct: the error rate is `p=exp(-ln(2)^2 * m/n)`
  * It is quite fast
     * It can **insert** around **2 million elements per second**.
     * It can **query** around **6 million elements per second**.
 
