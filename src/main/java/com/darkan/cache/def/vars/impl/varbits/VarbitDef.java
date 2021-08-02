package com.darkan.cache.def.vars.impl.varbits;

import com.darkan.cache.Cache;
import com.darkan.cache.def.vars.VarDomain;

public class VarbitDef {
	
	private static VarbitDefParser PARSER = new VarbitDefParser();
	
	public static VarbitDef get(int id) {
		return PARSER.get(Cache.get(), id);
	}

	public static VarbitDefParser getParser() {
		return PARSER;
	}
	
	public int id;
	public byte unk;
	public int baseVar;
	public int startBit;
	public int endBit;
	public VarDomain domain;
	
	@Override
	public String toString() {
		return "[" + id + " base: " + baseVar + " (" + startBit + " -> " + endBit + ") domain: (" + domain + ")]";
	}
}
