package com.darkan.cache.compression;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

public class BZIP2 {
	public static byte[] decompress(byte[] compressed) throws IOException {
        byte[] fixed = new byte[compressed.length + 4];
        System.arraycopy(compressed, 0, fixed, 4, compressed.length);
        fixed[0] = (byte) 'B';
        fixed[1] = (byte) 'Z';
        fixed[2] = (byte) 'h';
        fixed[3] = (byte) '1';

        BZip2CompressorInputStream is = new BZip2CompressorInputStream(new ByteArrayInputStream(fixed));
		byte[] uncompressed = is.readAllBytes();
		is.close();
		return uncompressed;
    }
}
