package com.darkan.cache.def.vars;

public enum BaseVarType {
	INTEGER(Integer.class),
	LONG(Long.class),
	STRING(String.class),
	COORDFINE(Integer.class); //TODO
	
	private Class<?> clazz;
	
	private BaseVarType(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public static BaseVarType forId(int id) {
		return BaseVarType.values()[id];
	}

	public Class<?> getClazz() {
		return clazz;
	}
}
