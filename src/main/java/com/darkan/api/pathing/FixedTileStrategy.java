package com.darkan.api.pathing;

import com.darkan.api.world.WorldTile;

public class FixedTileStrategy extends RouteStrategy {

	private WorldTile target;

	public FixedTileStrategy(WorldTile target) {
		this.target = target;
	}

	@Override
	public boolean canExit(int currentX, int currentY, int sizeXY, int[][] clip, int clipBaseX, int clipBaseY) {
		return currentX == target.getX() && currentY == target.getY();
	}

	@Override
	public int getApproxDestinationX() {
		return target.getX();
	}

	@Override
	public int getApproxDestinationY() {
		return target.getY();
	}

	@Override
	public int getApproxDestinationSizeX() {
		return 1;
	}

	@Override
	public int getApproxDestinationSizeY() {
		return 1;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof FixedTileStrategy)) {
			return false;
		}
		FixedTileStrategy strategy = (FixedTileStrategy) other;
		return target.getX() == strategy.target.getX() && target.getY() == strategy.target.getY();
	}
}
