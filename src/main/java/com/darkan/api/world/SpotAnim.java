package com.darkan.api.world;

import kraken.plugin.api.Effect;

public class SpotAnim {
	
	private int id;
	private WorldTile position;
	
	public SpotAnim(Effect e) {
		this.id = e.getId();
		this.position = new WorldTile(e.getGlobalPosition());
	}

	public int getId() {
		return id;
	}

	public WorldTile getPosition() {
		return position;
	}
	
}
