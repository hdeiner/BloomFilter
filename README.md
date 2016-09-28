BloomFilter
===========
BloomFilter implements a Bloom filter in Java. 

Bloom Filters allow one to check for duplicates in lists of entities, such as a dictionary. They are fast and space-efficient, however, because they rely on hashing, there is a certain probability of error. A Bloom Filters will never produce a false positive (that the new and unique entity is already present), but they never produce false negatives.

Guide to this code
------------------

This is a very simple Bloom Filter.  It consists of two files.  

The MD5.java file digests a String through the java.security.MessageDigest using the MD5 algorithm.  This is a well known cryptographic hash function that produces 128 hash values.

The BloomFilter.java file implements the Bloom Filter itself.  You construct a Bloom Filter as follows:

```java
int expectedElementsCount = /^[\d]+$/;       // estimated number of entitites that will be processed through the filter
int bitsPerElement = /^[\d]+$/;              // estimated number of bits in a entity
int numberOfHashFunctionsToUse = /^[\d]+$/;  // number of times different hashes are used to index into the Bloom Filter 
BloomFilter bloomFilter = new BloomFilter(expectedElementsCount, bitsPerElement, numberOfHashFunctionsToUse);
```

After the Bloom filter has been created, new elements may be added using it's add() method.

```java
bloomFilter.add("Cat");
bloomFilter.add("Dog");
```

To see if an element has been already been stored in the Bloom filter, use the contains() method. 

```java
bloomFilter.contains("Dog");   // returns true
bloomFilter.contains("Mouse"); // returns false
```

Internally, this implementation of a Bloom Filter uses a BitSet.  The size of the BitSet is dependent on the values passed into it's constructor.  There is a trade-off of space used against false positives returned.  A similar dynamic is at play for the number of hashes to use.  Less hashes will use less CPU at the expense of false positives.