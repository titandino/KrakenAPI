package com.darkan.api.inter;

import java.util.HashMap;
import java.util.Map;

public enum ComponentType {
	CONTAINER(0),
	TYPE_1(1),
	TYPE_2(2),
	FIGURE(3), 
	TEXT(4), 
	SPRITE(5), 
	MODEL(6),
	TYPE_7(7),
	TYPE_8(8),
	LINE(9),
	TYPE_10(10),
	TYPE_11(11),
	TYPE_12(12),
	TYPE_13(13),
	TYPE_14(14),
	TYPE_15(15),
	TYPE_16(16);
	
	private static Map<Integer, ComponentType> MAP = new HashMap<>();

	static {
		for (ComponentType t : ComponentType.values())
			MAP.put(t.id, t);
	}
	
	public static ComponentType forId(int id) {
		return MAP.get(id);
	}

	private int id;

	private ComponentType(int id) {
		this.id = id;
	}
}
