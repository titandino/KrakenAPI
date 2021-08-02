package com.darkan.cache.def.structs;

import com.darkan.cache.Cache;
import com.darkan.cache.def.params.Params;

public class StructDef {
	public int id;
	public Params params = new Params();
	
	private static StructDefParser PARSER = new StructDefParser();
	
	public static StructDefParser getParser() {
		return PARSER;
	}
	
	public static StructDef get(int id) {
		return PARSER.get(Cache.get(), id);
	}
	
	@Override
	public String toString() {
		return params.toString();
	}
}
