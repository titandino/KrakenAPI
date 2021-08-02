package com.darkan.cache.compression;

public enum Compression {
	NONE((byte) 0),
	BZIP((byte) 1),
	GZIP((byte) 2),
	ZLIB((byte) 'Z', (byte) 'L', (byte) 'B');
	
	private byte[] header;
	
	private Compression(byte... header) {
		this.header = header;
	}
	
	public static Compression forData(byte[] data) {
		for (Compression c : Compression.values()) {
			boolean matches = true;
			for (int i = 0;i < c.header.length;i++) {
				if (data[i] != c.header[i])
					matches = false;
			}
			if (matches)
				return c;
		}
		return null;
	}
}
