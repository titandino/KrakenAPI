package com.darkan.cache.def.params;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.darkan.Constants;
import com.darkan.api.util.Utils;
import com.darkan.api.world.WorldTile;
import com.darkan.cache.def.items.ItemDef;
import com.darkan.cache.def.npcs.NPCDef;
import com.darkan.cache.def.vars.ScriptVarType;

public class Params {
	
	public Map<Integer, Object> map = new HashMap<>();
	
	public void parse(ByteBuffer buffer) {
		int size = buffer.get() & 0xFF;
		for (int index = 0; index < size; index++) {
			boolean bool = (buffer.get() & 0xFF) == 1;
			int key = Utils.getTriByte(buffer);
			Object value = bool ? Utils.getString(buffer) : buffer.getInt();
			map.put(key, value);
		}
	}

	public Object valToType(int id, Object o) {
		if (ParamDef.get((int) id).type == ScriptVarType.COMPONENT) {
			if (o instanceof String)
				return o;
			long interfaceId = ((int) o) >> 16;
			long componentId = ((int) o) - (interfaceId << 16);
			return "IComponent("+interfaceId+", "+componentId+")";
		} else if (ParamDef.get((int) id).type  == ScriptVarType.WORLDTILE) {
			if (o instanceof String)
				return o;
			return new WorldTile(((int) o));
		} else if (ParamDef.get((int) id).type  == ScriptVarType.STAT) {
			if (o instanceof String)
				return o;
			int idx = (int) o;
			if (idx >= Constants.SKILL_NAME.length)
				return o;
			return idx+"("+Constants.SKILL_NAME[((int) o)]+")";
		} else if (ParamDef.get((int) id).type  == ScriptVarType.ITEM) {
			if (o instanceof String)
				return o;
			return ((int) o)+ (((int) o) != -1 ? " ("+ItemDef.get(((int) o)).name+")" : ""); 
		} else if (ParamDef.get((int) id).type  == ScriptVarType.NPC) {
			if (o instanceof String)
				return o;
			return ((int) o)+ (((int) o) != -1 ? " ("+NPCDef.get(((int) o)).name+")" : ""); 
		} else if (ParamDef.get((int) id).type  == ScriptVarType.STRUCT) {
			return o/* + ": " + StructDef.get((int) o)*/; 
		}
		return o;
	}
	
	@Override
	public String toString() {
		if (map == null)
			return "null";
		StringBuilder s = new StringBuilder();
		s.append("{ ");
		for (Integer l : map.keySet()) {
			s.append(l + " ("+ParamDef.get(l).type+")");
			s.append(" = ");
			s.append(valToType(l, map.get(l)) + ", ");
		}
		s.append(" }");
		return s.toString();
	}

	public Object get(int id) {
		return map.get(id);
	}
	
	public String getString(int opcode, String defaultVal) {
		if (map != null) {
			Object value = map.get(opcode);
			if (value != null && value instanceof String)
				return (String) value;
		}
		return defaultVal;
	}

	public String getString(int opcode) {
		if (map != null) {
			Object value = map.get(opcode);
			if (value != null && value instanceof String)
				return (String) value;
		}
		return "null";
	}
	
	public int getInt(int opcode, int defaultVal) {
		if (map != null) {
			Object value = map.get(opcode);
			if (value != null && value instanceof Integer)
				return (int) value;
		}
		return defaultVal;
	}

	public int getInt(int opcode) {
		if (map != null) {
			Object value = map.get(opcode);
			if (value != null && value instanceof Integer)
				return (int) value;
		}
		return 0;
	}
}
