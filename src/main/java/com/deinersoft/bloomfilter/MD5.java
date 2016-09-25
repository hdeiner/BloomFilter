package com.deinersoft.bloomfilter;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    private byte[] hash;

    MD5(String input) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        byte[] bytesOfMessage = input.getBytes();
        MessageDigest md = MessageDigest.getInstance("MD5");
        hash = md.digest(bytesOfMessage);

    }

    @Override
    public String toString(){

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
            }
            else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        return hexString.toString();
    }
}
