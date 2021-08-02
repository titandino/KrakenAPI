package com.darkan.cache.def.vars;

import java.util.HashMap;
import java.util.Map;

public enum VarDomain {
	PLAYER(0, 60), 
	NPC(1, 61), 
	CLIENT(2, 62), 
	WORLD(3, 63), 
	REGION(4, 64), 
	OBJECT(5, 65), 
	CLAN(6, 66), 
	CLAN_SETTING(7, 67),
	UNK(8, 68);
	
	private static Map<Integer, VarDomain> MAP = new HashMap<>();
	
	static {
		for (VarDomain d : VarDomain.values())
			MAP.put(d.id, d);
	}
	
	public static VarDomain forId(int id) {
		return MAP.get(id);
	}

	private int id;
	private int configArchive;

	private VarDomain(int id, int configArchive) {
		this.id = id;
		this.configArchive = configArchive;
	}

	public int getId() {
		return id;
	}

	public int getConfigArchive() {
		return configArchive;
	}
}
