package com.deinersoft.bloomfilter;

import com.deinersoft.bloomfilter.hashes.MD5;

import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class BloomFilter {

    private final int numberOfHashFunctionsToUse;
    private final int bitSetSize;
    private final BitSet bitSet;

    public BloomFilter(int expectedElementsCount, int bitsPerElement, int numberOfHashFunctionsToUse) {
        this.numberOfHashFunctionsToUse = numberOfHashFunctionsToUse;
        this.bitSetSize = (bitsPerElement * expectedElementsCount);
        this.bitSet = new BitSet(bitSetSize);
    }

    public BitSet getBitSet() {
        return bitSet;
    }

    public void add(String word) throws NoSuchAlgorithmException {
        int[] hashes = createHashes(word);
        for (int hash : hashes)
            bitSet.set(Math.abs(hash % bitSetSize), true);
    }

    private int[] createHashes(String word) throws NoSuchAlgorithmException {
        int[] hashes = new int[numberOfHashFunctionsToUse];
        for (int i=0; i<numberOfHashFunctionsToUse; i++) {
            byte[] hashBytes = new MD5(word).getHash();
            hashes[i] = (hashBytes[i]<<24) & 0xff000000 | (hashBytes[i+1]<<16) & 0x00ff0000 | (hashBytes[i+2]<< 8) & 0x0000ff00 | (hashBytes[i+3]) & 0x000000ff;
        }
        return hashes;
    }

    public boolean contains(String word) throws NoSuchAlgorithmException {
        boolean result = true;
        int[] hashes = createHashes(word);
        for (int hash : hashes)
            result &= bitSet.get(Math.abs(hash % bitSetSize));
        return result;
    }

}



