package com.darkan.cache.def.vars.impl.varbits;

import java.nio.ByteBuffer;

import com.darkan.cache.Archive;
import com.darkan.cache.Cache;
import com.darkan.cache.Index;
import com.darkan.cache.def.CacheParser;
import com.darkan.cache.def.vars.VarDomain;

public class VarbitDefParser extends CacheParser<VarbitDef> {

	@Override
	public Index getIndex() {
		return Index.CONFIG;
	}
	
	@Override
	public int getArchiveId(int id) {
		return 69;
	}
	
	@Override
	public int getFileId(int id) {
		return id;
	}
	
	@Override
	public int getArchiveBitSize() {
		return 1;
	}
	
	@Override
	public int getMaxId() {
		Archive archive = Cache.get().getArchive(getIndex(), getArchiveId(0));
		return archive.files.keySet().stream().max((i1, i2) -> i1 - i2).get().intValue();
	}

	@Override
	public VarbitDef decode(int id, ByteBuffer buffer) {
		VarbitDef def = new VarbitDef();
		def.id = id;
		while (buffer.hasRemaining()) {
			int opcode = buffer.get() & 0xff;
			if (opcode == 0)
				break;

			switch (opcode) {
			case 1:
				def.unk = (byte) (buffer.get() & 0xff);
				def.domain = VarDomain.forId(def.unk);
				def.baseVar = buffer.getShort() & 0xffff;
				break;
			case 2:
				def.startBit = buffer.get() & 0xff;
				def.endBit = buffer.get() & 0xff;
				break;
			default:
				throw new IllegalArgumentException("Invalid VarbitDefintion opcode " + opcode);
			}
		}
		return def;
	}
}
