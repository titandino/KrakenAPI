package com.darkan.cache.def.params;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.darkan.api.util.Utils;
import com.darkan.cache.Index;
import com.darkan.cache.def.CacheParser;
import com.darkan.cache.def.vars.ScriptVarType;

public class ParamDefParser extends CacheParser<ParamDef> {

	@Override
	public Index getIndex() {
		return Index.CONFIG;
	}
	
	@Override
	public int getArchiveBitSize() {
		return 1;
	}
	
	@Override
	public int getArchiveId(int id) {
		return 11;
	}

	@Override
	public int getFileId(int id) {
		return id;
	}

	@Override
	public ParamDef decode(int id, ByteBuffer buffer) {
		ParamDef def = new ParamDef();
		def.id = id;
		while (buffer.hasRemaining()) {
			int opcode = buffer.get() & 0xff;
			if (opcode == 0)
				break;
			if (opcode == 101) {
				def.typeId = Utils.getUnsignedSmart(buffer);
				def.type = ScriptVarType.getById(def.typeId);
			} else if (opcode == 2) {
				def.defaultInt = buffer.getInt();
			} else if (opcode == 4) {
				def.autoDisable = false;
			} else if (opcode == 5) {
				def.defaultString = Utils.getString(buffer);
			} else {
				throw new IllegalArgumentException("Invalid ParamDefinition opcode " + opcode + " -> " + Arrays.toString(buffer.array()));
			}
		}
		return def;
	}
}
