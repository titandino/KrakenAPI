package com.darkan.api.item;

import com.darkan.api.util.Utils;
import com.darkan.api.world.Interactable;
import com.darkan.api.world.WorldTile;
import com.darkan.cache.def.items.ItemDef;

import kraken.plugin.api.Actions;

public class GroundItem implements Interactable {

	private int id;
	private WorldTile position;
	
	public GroundItem(kraken.plugin.api.GroundItem item) {
		this.id = item.getId();
		this.position = new WorldTile(item.getGlobalPosition());
	}
	
	public ItemDef getDef() {
		return ItemDef.get(id);
	}

	public int getId() {
		return id;
	}

	public WorldTile getPosition() {
		return position;
	}

	@Override
	public String name() {
		return getDef().name;
	}

	@Override
	public boolean hasOption(String option) {
		return getDef().containsGroundOp(option);
	}

	@Override
	public boolean interact(int option) {
		if (option == 2) {
			Actions.menu(Actions.MENU_EXECUTE_GROUND_ITEM, getId(), getPosition().getX(), getPosition().getY(), Utils.random(0, Integer.MAX_VALUE));
			return true;
		}
		return false;
	}

	@Override
	public boolean interact(String action) {
		int op = getDef().getGroundOpIdForName(action);
		if (op != -1) {
			interact(op);
			return true;
		}
		return false;
	}
	
}
