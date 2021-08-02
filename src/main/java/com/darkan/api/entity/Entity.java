package com.darkan.api.entity;

import com.darkan.api.pathing.Direction;
import com.darkan.api.world.WorldTile;

public abstract class Entity {

	protected WorldTile position;
	protected int serverIndex;
	protected boolean moving;
	protected int animationId;
	protected boolean animationPlaying;
	protected int interactingIndex;
	protected Direction dir;

	public Entity(kraken.plugin.api.Spirit spirit) {
		if (spirit != null) {
			this.position = new WorldTile(spirit.getGlobalPosition());
			this.serverIndex = spirit.getServerIndex();
			this.moving = spirit.isMoving();
			this.animationId = spirit.getAnimationId();
			this.animationPlaying = spirit.isAnimationPlaying();
			this.interactingIndex = spirit.getInteractingIndex();
			this.dir = Direction.forDelta((int) spirit.getDirectionOffset().getX(), (int) spirit.getDirectionOffset().getY());
		}
	}
	
	public Entity(WorldTile position) {
		this.position = position;
	}

	public abstract int getSize();
	
	
	public WorldTile getPosition() {
		return position;
	}
	
	public int getServerIndex() {
		return serverIndex;
	}

	public boolean isMoving() {
		return moving;
	}

	public int getAnimationId() {
		return animationId;
	}

	public boolean isAnimationPlaying() {
		return animationPlaying;
	}

	public int getInteractingIndex() {
		return interactingIndex;
	}

	public Direction getDir() {
		return dir;
	}

}
