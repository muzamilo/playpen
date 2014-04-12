package com.appedia.bassat.common;

/**
 *
 * @author Muz Omar
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressionUtil {

    /**
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] compress(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        try {
            deflater.setInput(data);
            deflater.finish();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
            try {
                byte[] buffer = new byte[1024];
                while (!deflater.finished()) {
                    int count = deflater.deflate(buffer); // returns the generated code... index
                    outputStream.write(buffer, 0, count);
                }
                return outputStream.toByteArray();
            } finally {
                outputStream.close();
            }
        } finally {
            deflater.end();
        }
    }

    /**
     *
     * @param data
     * @return
     * @throws IOException
     * @throws DataFormatException
     */
    public static byte[] decompress(byte[] data) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        try {
            inflater.setInput(data);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
            try {
                byte[] buffer = new byte[1024];
                while (!inflater.finished()) {
                    int count = inflater.inflate(buffer);
                    outputStream.write(buffer, 0, count);
                }
                return outputStream.toByteArray();
            } finally {
                outputStream.close();
            }
        } finally {
            inflater.end();
        }
    }

    public static void main(String[] args) throws Exception {
        String s = "Hello World";
        System.out.println(s);
        System.out.println(new String(compress(s.getBytes())));
        System.out.println(new String(decompress(compress(s.getBytes()))));
    }
}