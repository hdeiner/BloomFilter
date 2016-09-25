package com.deinersoft.bloomfilter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class MD5 {

    private byte[] hash;

    MD5(String input) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        hash = md.digest(input.getBytes());

    }

    @Override
    public String toString(){

        StringBuilder hexString = new StringBuilder();
        for (byte hashByte : hash) {
            hexString.append(String.format("%02x", hashByte));
        }
        return hexString.toString();
    }
}
