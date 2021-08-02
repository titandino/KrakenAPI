package com.darkan.cache;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.darkan.api.util.Logger;
import com.darkan.cache.compression.BZIP2;
import com.darkan.cache.compression.Compression;
import com.darkan.cache.compression.GZIP;
import com.darkan.cache.compression.ZLIB;

class Container {

	public byte[] data;
	public Compression compression = Compression.ZLIB;

	public Container(byte[] data, Compression compression) {
		this.data = data;
		this.compression = compression;
	}

	public static Container decode(ByteBuffer data, Archive archive) {
		try {
			if (!data.hasRemaining())
				throw new IllegalArgumentException("Provided non-readable (empty?) buffer");

			Compression compression = Compression.forData(data.array());
			byte[] compressed = data.array();
			byte[] decompressed = switch (compression) {
				case BZIP -> BZIP2.decompress(compressed);
				case GZIP -> GZIP.decompress(compressed);
				case ZLIB -> ZLIB.decompress(compressed);
				default -> compressed;
			};
			return new Container(decompressed, compression);
		} catch (IOException e) {
			System.err.println("Error decompressing container data...");
			Logger.handle(e);
			return null;
		}
	}

	public static ByteBuffer wrap(ByteBuffer data) {
		ByteBuffer buf = ByteBuffer.allocate(5 + data.remaining());
		buf.put((byte) Compression.NONE.ordinal());
		buf.putInt(data.remaining());
		buf.put(data);
		buf.flip();
		return buf;
	}

	public static ByteBuffer wrap(byte[] data) {
		ByteBuffer buf = ByteBuffer.allocate(5 + data.length);
		buf.put((byte) Compression.NONE.ordinal());
		buf.putInt(data.length);
		buf.put(data);
		buf.flip();
		return buf;
	}
}