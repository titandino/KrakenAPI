package com.darkan.api.inter;

import com.darkan.api.item.Item;
import com.darkan.api.util.Utils;
import kraken.plugin.api.Actions;
import kraken.plugin.api.Widgets;

public class IFSlot {

	private int interfaceId;
	private int componentId;
	private int slotId;

	public IFSlot(int id, int componentId, int slotId) {
		this.interfaceId = id;
		this.componentId = componentId;
		this.slotId = slotId;
	}

	public boolean click(int option) {
		if (isOpen()) {
			Actions.menu(Actions.MENU_EXECUTE_WIDGET, option, slotId, getHash(), Utils.random(0, Integer.MAX_VALUE));
			return true;
		}
		return false;
	}

	public int getHash() {
		return interfaceId << 16 | componentId;
	}
	
	public boolean isOpen() {
		try {
			return Widgets.getGroupById(interfaceId).getWidgets()[componentId].getChildren()[slotId] != null;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String getText() {
		try {
			return Widgets.getGroupById(interfaceId).getWidgets()[componentId].getChildren()[slotId].getText();
		} catch (Exception e) {
			return null;
		}
	}
	
	public ComponentType getType() {
		try {
			return ComponentType.forId(Widgets.getGroupById(interfaceId).getWidgets()[componentId].getChildren()[slotId].getType());
		} catch (Exception e) {
			return null;
		}
	}
	
	public Item getItem() {
		try {
			kraken.plugin.api.Item item = Widgets.getGroupById(interfaceId).getWidgets()[componentId].getChildren()[slotId].getItem();
			return new Item(this, item.getId(), item.getAmount());
		} catch (Exception e) {
			return null;
		}
	}

	public int getInterfaceId() {
		return interfaceId;
	}

	public int getComponentId() {
		return componentId;
	}
	
	public int getSlotId() {
		return slotId;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("\t\tIFSlot: (" + interfaceId + ", " + componentId + ", " + slotId + ", " + getType() + ")\r\n");
		String text = getText();
		if (text != null)
			s.append("\t\tText: \"" + text + "\"\r\n");
		Item item = getItem();
		if (item != null)
			s.append("\t\tItem: \"" + item + "\"\r\n");
		return s.toString();
	}
}

