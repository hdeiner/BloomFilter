package test.com.deinersoft.bloomfilter.hashes;

import com.deinersoft.bloomfilter.hashes.MD5;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

//
// Feeling fear in using MD5, these tests show that I (and Java security) can get proper hash results
// see https://en.wikipedia.org/wiki/MD5
//

public class MD5Test {

    @Test
    public void informalTest1() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MD5 md5 = new MD5("The quick brown fox jumps over the lazy dog");
        assertThat(md5.toString(), is("9e107d9d372bb6826bd81d3542a419d6"));
    }

    @Test
    public void informalTest2() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MD5 md5 = new MD5("The quick brown fox jumps over the lazy dog.");
        assertThat(md5.toString(), is("e4d909c290d0fb1ca068ffaddf22cbd0"));
    }

    @Test
    public void informalTest3() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MD5 md5 = new MD5("");
        assertThat(md5.toString(), is("d41d8cd98f00b204e9800998ecf8427e"));
    }

}
