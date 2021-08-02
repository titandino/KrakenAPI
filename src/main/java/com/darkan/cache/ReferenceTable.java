package com.darkan.cache;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.darkan.api.util.Utils;

public class ReferenceTable {
	public Cache cache;
	public int version = 0;
	public int format = 7;
	public int mask = 0;
	public int index;
	public Map<Integer, Archive> archives = new HashMap<>();

	public ReferenceTable(Cache cache, int index) {
		this.cache = cache;
		this.index = index;
	}

	public int readFormatInt(ByteBuffer buffer) {
		if (format >= 7)
			return Utils.getSmartInt(buffer);
		else
			return buffer.getShort() & 0xffff;
	}

	public void decode(ByteBuffer buffer) {
		format = buffer.get();
		if (format < 5 || format > 7)
			throw new IllegalArgumentException("Reference table format not in range 5..7 (" + format + ")");
		version = (format >= 6) ? buffer.getInt() : 0;
		mask = buffer.get();

		boolean hasNames = (mask & 0x1) != 0;
		boolean hasWhirlpools = (mask & 0x2) != 0;
		boolean hasSizes = (mask & 0x4) != 0;
		boolean hasHashes = (mask & 0x8) != 0;

		int[] archiveIds = new int[readFormatInt(buffer)];
		for (int i = 0; i < archiveIds.length; i++) {
			int archiveId = readFormatInt(buffer) + ((i == 0) ? 0 : archiveIds[i - 1]);
			archiveIds[i] = archiveId;
			archives.put(archiveId, new Archive(archiveId));
		}

		if (hasNames) {
			for (int archiveId : archiveIds)
				archives.get(archiveId).name = buffer.getInt();
		}

		for (int archiveId : archiveIds)
			archives.get(archiveId).crc = buffer.getInt();

		if (hasHashes) {
			for (int archiveId : archiveIds)
				archives.get(archiveId).hash = buffer.getInt();
		}

		if (hasWhirlpools) {
			for (int archiveId : archiveIds) {
				byte[] whirlpool = new byte[64];
				buffer.get(whirlpool);
				archives.get(archiveId).whirlpool = whirlpool;
			}
		}

		if (hasSizes) {
			for (int archiveId : archiveIds) {
				Archive archive = archives.get(archiveId);
				archive.compressedSize = buffer.getInt();
				archive.uncompressedSize = buffer.getInt();
			}
		}

		for (int archiveId : archiveIds)
			archives.get(archiveId).version = buffer.getInt();

		int[][] archiveFileIds = new int[archives.size()][];
		for (int i = 0; i < archiveFileIds.length; i++)
			archiveFileIds[i] = new int[readFormatInt(buffer)];

		for (int i = 0; i < archiveIds.length; i++) {
			Archive archive = archives.get(archiveIds[i]);
			int[] fileIds = archiveFileIds[i];
			int fileId = 0;
			for (int j = 0; j < fileIds.length; j++) {
				fileId += readFormatInt(buffer);
				archive.files.put(fileId, new ArchiveFile(fileId));
				fileIds[j] = fileId;
			}
		}

		if (hasNames) {
			for (int i = 0; i < archiveIds.length; i++) {
				Archive archive = archives.get(archiveIds[i]);
				int[] fileIds = archiveFileIds[i];
				for (int j = 0; j < fileIds.length; j++)
					archive.files.get(fileIds[j]).name = buffer.getInt();
			}
		}
	}

	public int highestEntry() {
		if (archives.isEmpty())
			return 0;
		else
			return archives.keySet().stream().reduce((first, second) -> second).orElse(null) + 1;
	}

	public int archiveSize() {
		if ((mask & 0x4) != 0) {
			int sum = 0;
			for (Archive a : archives.values())
				sum += a.uncompressedSize;
			return sum;
		} else {
			int sum = 0;
			for (int key : archives.keySet()) {
				ByteBuffer data = cache.read(index, key);
				Container container = Container.decode(data, null);
				sum += container.data.length;
			}
			return sum;
		}
	}

	public long totalCompressedSize() {
		long sum = 0L;
		for (Archive a : archives.values())
			sum += a.compressedSize;
		return sum;
	}

	public Archive loadArchive(int id) {
		Archive archive = archives.get(id);
		if (archive == null)
			return null;
		if (archive.loaded)
			return archive;

		ByteBuffer raw = cache.read(index, id);
		ByteBuffer file = ByteBuffer.wrap(Container.decode(raw, archive).data);
		archive.decode(file);

		return archive;
	}
}