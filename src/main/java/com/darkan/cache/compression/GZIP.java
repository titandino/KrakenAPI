package com.darkan.cache.compression;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class GZIP {
	public static byte[] decompress(byte[] compressed) throws IOException {
        return new GZIPInputStream(new ByteArrayInputStream(compressed)).readAllBytes();
    }
}
