package com.deinersoft.bloomfilter;

import java.util.BitSet;

public class BloomFilter {

    private int expectedElementsCount;
    private int bitsPerElement;
    private int numberOfHashFunctionsToUse;
    private int bitSetSize;
    private BitSet bitSet;

    public BloomFilter(int expectedElementsCount, int bitsPerElement, int numberOfHashFunctionsToUse) {
        this.expectedElementsCount = expectedElementsCount;
        this.bitsPerElement = bitsPerElement;
        this.numberOfHashFunctionsToUse = numberOfHashFunctionsToUse;
        this.bitSetSize = (bitsPerElement * expectedElementsCount);
        this.bitSet = new BitSet(bitSetSize);
    }

    public BitSet getBitSet() {
        return bitSet;
    }

    public void add(String word) {
        return;
    }

    public boolean contains(String word) {
        return false;
    }

}



