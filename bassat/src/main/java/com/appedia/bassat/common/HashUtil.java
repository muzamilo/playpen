package com.appedia.bassat.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Muz Omar
 */
public class HashUtil {

    /**
     *
     * @param string
     * @param algorithm
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String hash(String string, String algorithm) throws UnsupportedEncodingException {
        return hash(string, algorithm, Charset.defaultCharset().toString());
    }

    /**
     *
     * @param string
     * @param algorithm
     * @param encoding
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String hash(String string, String algorithm, String encoding) throws UnsupportedEncodingException {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            digest.update(string.getBytes(encoding));

            byte[] encodedPassword = digest.digest();

            return new BigInteger(1, encodedPassword).toString(16);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param file
     * @param algorithm
     * @return
     * @throws IOException
     */
    public static String hash(File file, String algorithm) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, digest);

            byte[] buffer = new byte[8192];

            int bytesRead = 1;
            while ((bytesRead = digestInputStream.read(buffer)) != -1) { }

            byte[] hash = digest.digest();

            return new BigInteger(1, hash).toString(16);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);

        } finally {
            fileInputStream.close();
        }
    }

}