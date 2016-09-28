package test.com.deinersoft.bloomfilter;

import com.deinersoft.bloomfilter.BloomFilter;
import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BloomFilterTest {

    private int expectedElementsCount;
    private int bitsPerElement;
    private int numberOfHashFunctionsToUse;
    private BloomFilter bloomFilter;

    @Before
    public void initialize(){
        expectedElementsCount = 100000;
        bitsPerElement = 160;
        numberOfHashFunctionsToUse = 6;
        bloomFilter = new BloomFilter(expectedElementsCount, bitsPerElement, numberOfHashFunctionsToUse);
    }

    @Test
    public void bitSetSizeCorrect(){
        assertThat(bloomFilter.getBitSet().size(), is(expectedElementsCount*bitsPerElement*numberOfHashFunctionsToUse));
    }

    @Test
    public void emptyBloomFilterDoesNotContainTheWordCat() throws NoSuchAlgorithmException {
        assertThat(bloomFilter.contains("Cat"), is(false));
    }

    @Test
    public void emptyBloomFilterAddsCatAndThenContainsCat() throws NoSuchAlgorithmException {
        bloomFilter.add("Cat");
        assertThat(bloomFilter.contains("Cat"), is(true));
    }

    @Test
    public void emptyBloomFilterAddsCatAndDogs() throws NoSuchAlgorithmException {
        bloomFilter.add("Cat");
        bloomFilter.add("Dog");
        assertThat(bloomFilter.contains("Cat"), is(true));
        assertThat(bloomFilter.contains("Dog"), is(true));
    }
}
