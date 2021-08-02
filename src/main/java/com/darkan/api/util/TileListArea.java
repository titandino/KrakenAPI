package com.darkan.api.util;

import com.darkan.api.world.WorldTile;

import kraken.plugin.api.Vector3i;

public class TileListArea extends Area {

	private WorldTile[] tiles;
	
	public TileListArea(WorldTile... tiles) {
		super(null, null);
		this.tiles = tiles;
	}
	
	public TileListArea(int... coords) {
		super(null, null);
		tiles = new WorldTile[coords.length/2];
		for (int i = 0;i < coords.length;i += 2)
			tiles[i/2] = new WorldTile(coords[i], coords[i+1]);
	}
	
	@Override
	public boolean inside(WorldTile tile) {
		for (WorldTile t : tiles)
			if (t.isAt(tile.getX(), tile.getY()))
				return true;
		return false;
	}
	
	@Override
	public boolean inside(Vector3i tile) {
		for (WorldTile t : tiles)
			if (t.isAt(tile.getX(), tile.getY()))
				return true;
		return false;
	}
}
