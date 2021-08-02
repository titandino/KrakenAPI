package com.darkan.cache.def.structs;

import java.nio.ByteBuffer;

import com.darkan.cache.Index;
import com.darkan.cache.def.CacheParser;

public class StructDefParser extends CacheParser<StructDef> {

	@Override
	public Index getIndex() {
		return Index.STRUCT_DEF;
	}
	
	@Override
	public int getArchiveBitSize() {
		return 5;
	}

	@Override
	public StructDef decode(int id, ByteBuffer buffer) {
		StructDef def = new StructDef();
		def.id = id;
		while (buffer.hasRemaining()) {
			int opcode = buffer.get() & 0xff;
			if (opcode == 0)
				break;

			switch (opcode) {
			case 249:
				def.params.parse(buffer);
				break;
			default:
				throw new IllegalArgumentException("Invalid StructDefinition opcode " + opcode);
			}
		}
		return def;
	}
}
