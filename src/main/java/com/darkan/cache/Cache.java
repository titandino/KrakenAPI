package com.darkan.cache;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.darkan.Settings;
import com.darkan.api.util.Logger;
import com.darkan.api.util.Utils;

public class Cache {
	private static final Path PATH = Paths.get(Settings.getConfig().getCachePath());

	public boolean[] checkedReferenceTables = new boolean[255];
	public ReferenceTable[] cachedReferenceTables = new ReferenceTable[255];
	public IndexFile[] indices = new IndexFile[0];
	
	private static Cache instance;
	
	public static Cache get() {
		if (instance != null)
			return instance;
		return instance = new Cache();
	}

	public Cache() {
		try {
			if (!Files.exists(PATH))
				Files.createDirectories(PATH);
		} catch (IOException e) {
			Logger.handle(e);
		}

		indices = new IndexFile[255];
		for (int i = 0; i < 255; i++) {
			if (Files.exists(PATH.resolve("js5-" + i + ".jcache")))
				indices[i] = new IndexFile(PATH.resolve("js5-" + i + ".jcache"));
		}
	}

	public ReferenceTable getReferenceTable(Index index) {
		return getReferenceTable(index.getIndexId(), false);
	}

	public ReferenceTable getReferenceTable(int index, boolean ignoreChecked) {
		ReferenceTable cached = cachedReferenceTables[index];
		if (cached != null)
			return cached;

		if (ignoreChecked) {
			if (checkedReferenceTables[index])
				return null;
			checkedReferenceTables[index] = true;
		}

		ReferenceTable table = new ReferenceTable(this, index);
		ByteBuffer container = readReferenceTable(index);
		if (container == null)
			return null;
		ByteBuffer data = ByteBuffer.wrap(Container.decode(container, null).data);
		table.decode(data);
		cachedReferenceTables[index] = table;
		return table;
	}

	public boolean exists(int index, int archive) {
		if (index < 0 || index >= indices.length)
			throw new IndexOutOfBoundsException("Index out of bounds: " + index);

		return indices[index] == null ? false : indices[index].exists(archive);
	}

	public ByteBuffer read(int index, int archive) {
		if (index < 0 || index >= indices.length)
			throw new IndexOutOfBoundsException("Index out of bounds: " + index);

		return ByteBuffer.wrap(indices[index].getRaw(archive));
	}

	public ByteBuffer read(int index, String name) {
		ReferenceTable table = getReferenceTable(index, false);
		if (table == null)
			return null;
		int hash = Utils.toFilesystemHash(name);
		int id = -1;
		for (Archive archive : table.archives.values()) {
			if (archive.name == hash) {
				id = archive.id;
				break;
			}
		}
		if (id == -1)
			return null;
		return read(index, id);
	}

	public ByteBuffer readReferenceTable(int index) {
		if (index < 0 || index >= indices.length)
			throw new IndexOutOfBoundsException("Index out of bounds: " + index);
		byte[] table = indices[index].getRawTable();
		if (table == null)
			return null;
		return ByteBuffer.wrap(table);
	}

	public int numIndices() {
		return indices.length;
	}

	public Archive getArchive(Index index, int id) {
		ReferenceTable table = getReferenceTable(index);
		if (table == null)
			return null;
		return table.loadArchive(id);
	}
}
