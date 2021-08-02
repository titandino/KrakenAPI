package com.darkan.cache.def.enums;

import java.nio.ByteBuffer;

import com.darkan.api.util.Utils;
import com.darkan.cache.Index;
import com.darkan.cache.def.CacheParser;
import com.darkan.cache.def.vars.ScriptVarType;

public class EnumDefParser extends CacheParser<EnumDef> {

	@Override
	public Index getIndex() {
		return Index.ENUM_DEF;
	}
	
	@Override
	public int getArchiveBitSize() {
		return 8;
	}
	
	@Override
	public EnumDef decode(int id, ByteBuffer buffer) {
		EnumDef def = new EnumDef();
		def.id = id;
		while (buffer.hasRemaining()) {
			int opcode = buffer.get() & 0xff;
			if (opcode == 0)
				break;

			switch (opcode) {
			case 1:
				def.keyType = ScriptVarType.getByChar(Utils.cp1252ToChar(buffer.get()));
				break;
			case 2:
				def.valueType = ScriptVarType.getByChar(Utils.cp1252ToChar(buffer.get()));
				break;
			case 3:
				def.defaultString = Utils.getString(buffer);
				break;
			case 4:
				def.defaultInt = buffer.getInt();
				break;
			case 5:
			case 6:
			case 7:
			case 8:
				boolean stringValues = opcode == 5 || opcode == 7;
				if (opcode == 7 || opcode == 8)
					Utils.skip(buffer, 2);
				int size = buffer.getShort() & 0xffff;
				for (int i = 0; i < size; i++) {
					int key = (opcode == 5 || opcode == 6) ? buffer.getInt() : (buffer.getShort() & 0xffff);
					Object value = stringValues ? Utils.getString(buffer) : buffer.getInt();
					def.values.put(key, value);
				}

				break;
			case 101:
				int type = Utils.getSmallSmartInt(buffer);
				def.keyType = ScriptVarType.getById(type);
				break;
			case 102:
				type = Utils.getSmallSmartInt(buffer);
				def.valueType = ScriptVarType.getById(type);

				break;
			default:
				throw new IllegalArgumentException("Invalid EnumDefinition opcode " + opcode);
			}
		}
		return def;
	}
}
