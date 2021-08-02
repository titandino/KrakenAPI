package com.darkan.api.entity;

import com.darkan.api.world.WorldTile;

import kraken.plugin.api.Player;

public class MyPlayer {
	
	private static VarManager vars = new VarManager();
	private static Player myPlayer;
	private static int maxHealth;
	private static int health;

	public static double getHealthPerc() {
		return ((double) getHealth() / (double) getMaxHealth()) * 100.0;
	}

	public static VarManager getVars() {
		return vars;
	}

	public static Player get() {
		return myPlayer;
	}

	public static void set(Player self) {
		myPlayer = self;
	}

	public static WorldTile getPosition() {
		return new WorldTile(myPlayer.getGlobalPosition());
	}

	public static int getMaxHealth() {
		return maxHealth;
	}

	public static void setMaxHealth(int maxHealth) {
		MyPlayer.maxHealth = maxHealth;
	}

	public static int getHealth() {
		return health;
	}

	public static void setHealth(int health) {
		MyPlayer.health = health;
	}
	
}
