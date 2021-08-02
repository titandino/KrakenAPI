package com.darkan.api.util;

import java.awt.Color;

import com.darkan.api.world.WorldTile;

import kraken.plugin.api.Client;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Vector2i;
import kraken.plugin.api.Vector3;

public class Paint {
	
	public static void rect(int x, int y, int width, int height, Color color) {
		ImGui.freePoly4(new Vector2i(x, y + height), new Vector2i(x + width, y + height), new Vector2i(x + width, y), new Vector2i(x, y), color.getRGB());
	}
	
	public static boolean drawTile(WorldTile tile, int color, boolean fill) {
		Vector3[] tileCorners = new Vector3[] { new Vector3(tile.getXDraw(), tile.getYDraw() + 512f, 797.0f), new Vector3(tile.getXDraw() + 512f, tile.getYDraw() + 512f, 797.0f), new Vector3(tile.getXDraw() + 512f, tile.getYDraw(), 797.0f), new Vector3(tile.getXDraw(), tile.getYDraw(), 797.0f) };
//		Vector2i[] tileCornersProj = new Vector2i[4];
//		if (Client.multiWorldToScreen(tileCorners, tileCornersProj)) {
//			ImGui.freePoly4(tileCornersProj[0], tileCornersProj[1], tileCornersProj[2], tileCornersProj[3], color);
//			return true;
//		}
//		return false;
		
		ImGui.freePoly4(Client.worldToMinimap(tileCorners[0]), Client.worldToMinimap(tileCorners[1]), Client.worldToMinimap(tileCorners[2]), Client.worldToMinimap(tileCorners[3]), color);
		ImGui.freeText("Drawing?", Client.worldToMinimap(new Vector3(tile.getXDraw(), tile.getYDraw(), 797.0f)), color);
		return true;
	}

}
