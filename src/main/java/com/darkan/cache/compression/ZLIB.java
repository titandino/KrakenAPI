package com.darkan.cache.compression;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import com.jcraft.jzlib.InflaterInputStream;

public class ZLIB {

	public static byte[] decompress(byte[] compressed) throws IOException {
		InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(Arrays.copyOfRange(compressed, 8, compressed.length)));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		shovelInToOut(in, out);
		byte[] decompressed = out.toByteArray();
		in.close();
		out.close();
		return decompressed;
	}

	private static void shovelInToOut(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1000];
		int len;
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
	}
}
