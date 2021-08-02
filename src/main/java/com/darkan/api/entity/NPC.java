package com.darkan.api.entity;

import com.darkan.api.util.Utils;
import com.darkan.api.world.Interactable;
import com.darkan.api.world.WorldTile;
import com.darkan.cache.def.npcs.NPCDef;

import kraken.plugin.api.Actions;
import kraken.plugin.api.Npc;

public class NPC extends Entity implements Interactable {
	
	private int id;
	private int health;
	
	public NPC(int id, WorldTile position) {
		super(position);
		this.id = id;
	}

	public NPC(Npc n) {
		super(n);
		this.id = n.getId();
		this.health = n.getHealth();
		this.serverIndex = n.getServerIndex();
	}

	@Override
	public int getSize() {
		return getDef().size;
	}

	public int getId() {
		return id;
	}

	public NPCDef getDef() {
		return NPCDef.get(id, MyPlayer.getVars());
	}

	@Override
	public boolean interact(String string) {
		int op = getDef().getOpIdForName(string);
		if (op != -1) {
			interact(op);
			return true;
		}
		return false;
	}

	@Override
	public boolean interact(int option) {
		if (option < 0 || option > 5)
			return false;
		Actions.menu(Actions.MENU_EXECUTE_NPC1 + option, getServerIndex(), 0, 0, Utils.random(0, Integer.MAX_VALUE));
		return true;
	}

	public String getName() {
		NPCDef def = getDef();
		if (def == null)
			return "";
		return def.name;
	}

	@Override
	public boolean hasOption(String string) {
		return getDef().containsOp(string);
	}
	
	@Override
	public String toString() {
		return "[" + id + ", " + getName() + ", " + getPosition() + "]";
	}

	@Override
	public String name() {
		return getName();
	}

	public int getHealth() {
		return health;
	}
}
