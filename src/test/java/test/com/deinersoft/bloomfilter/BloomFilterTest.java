package test.com.deinersoft.bloomfilter;

import com.deinersoft.bloomfilter.BloomFilter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BloomFilterTest {

    private static int actualElementsCount;
    private int expectedElementsCount;
    private int bitsPerElement;
    private int numberOfHashFunctionsToUse;
    private BloomFilter bloomFilter;

    @BeforeClass
    public static void getActualElementCount() throws IOException {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("resources/words"))) {
            while (bufferedReader.readLine() != null ) {
                actualElementsCount++;
            }
        }

    }

    @Before
    public void initialize(){
        expectedElementsCount = 250000;
        bitsPerElement = 40;
        numberOfHashFunctionsToUse = 5;
        bloomFilter = new BloomFilter(expectedElementsCount, bitsPerElement, numberOfHashFunctionsToUse);
    }

    @Test
    public void bitSetSizeCorrect(){
        assertThat(bloomFilter.getBitSet().size(), is(expectedElementsCount*bitsPerElement));
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

    @Test
    public void wordsFileChecker() throws IOException, NoSuchAlgorithmException {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("resources/words"))) {
            for(String word; (word = bufferedReader.readLine()) != null; ) {
                assertThat(bloomFilter.contains(word), is(false));
                bloomFilter.add(word);
                assertThat(bloomFilter.contains(word), is(true));
            }
        }
    }
    @Test
    public void testFilterUsing3Hashes() throws IOException, NoSuchAlgorithmException {
        numberOfHashFunctionsToUse = 3;
        bloomFilter = new BloomFilter(expectedElementsCount, bitsPerElement, numberOfHashFunctionsToUse);
        int falsePositives = countFalsePositivesDuringInsert();
        printTestResults("testFilterUsing3Hashes", expectedElementsCount, bitsPerElement, numberOfHashFunctionsToUse, falsePositives);
    }

    @Test
    public void testFilterUsing20bitsPerElement() throws IOException, NoSuchAlgorithmException {
        bitsPerElement = 20;
        bloomFilter = new BloomFilter(expectedElementsCount, bitsPerElement, numberOfHashFunctionsToUse);
        int falsePositives = countFalsePositivesDuringInsert();
        printTestResults("testFilterUsing32bitsPerElement", expectedElementsCount, bitsPerElement, numberOfHashFunctionsToUse, falsePositives);
    }

    @Test
    public void testFilterUsing100000expectedElementsCount() throws IOException, NoSuchAlgorithmException {
        expectedElementsCount = 100000;
        bloomFilter = new BloomFilter(expectedElementsCount, bitsPerElement, numberOfHashFunctionsToUse);
        int falsePositives = countFalsePositivesDuringInsert();
        printTestResults("testFilterUsing100000expectedElementsCount", expectedElementsCount, bitsPerElement, numberOfHashFunctionsToUse, falsePositives);
    }

    private int countFalsePositivesDuringInsert() throws IOException, NoSuchAlgorithmException {
        int falsePositives = 0;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("resources/words"))) {
            for(String word; (word = bufferedReader.readLine()) != null; ) {
                if (!bloomFilter.contains(word)) {
                    bloomFilter.add(word);
                } else {
                    falsePositives++;
                }
            }
        }
        return falsePositives;
    }

    private void printTestResults(String testName, int expectedElementsCount, int bitsPerElement, int numberOfHashFunctionsToUse, int falsePositives) {
        System.out.println("");
        System.out.println("testName                     = " + testName);
        System.out.println("actualElementsCount          = " + Integer.toString(actualElementsCount));
        System.out.println("expectedElementsCount        = " + Integer.toString(expectedElementsCount));
        System.out.println("bitsPerElement               = " + Integer.toString(bitsPerElement));
        System.out.println("numberOfHashFunctionsToUse   = " + Integer.toString(numberOfHashFunctionsToUse));
        System.out.println("False positives              = " + Integer.toString(falsePositives));
        System.out.println("actualFalsePositiveRate      = " + String.format("%.2f",(float)(falsePositives)/(float)(actualElementsCount)*100.0)+"%");

        System.out.println("theoreticalFalsePositiveRate = " + String.format("%.2f",theoreticalFalsePositiveRate()*100.0)+"%");
    }

    double theoreticalFalsePositiveRate () {
        // (1 - e^(k * n / m)) ^ k
        double result = Math.pow(1.0 -
                            Math.exp(((1.0*numberOfHashFunctionsToUse)*(double)actualElementsCount)
                                    / (((double) bitsPerElement)*(double)expectedElementsCount))
                        ,numberOfHashFunctionsToUse);
        return Math.abs(result);
    }
}
