package com.darkan.api.inter;

import com.darkan.api.item.Item;
import com.darkan.api.util.Utils;

import kraken.plugin.api.Actions;
import kraken.plugin.api.Widgets;

public class IFComponent {

	protected int interfaceId;
	protected int componentId;

	public IFComponent(int id, int componentId) {
		this.interfaceId = id;
		this.componentId = componentId;
	}

	public boolean click(int option, int slotId) {
		if (isOpen()) {
			Actions.menu(Actions.MENU_EXECUTE_WIDGET, option, slotId, getHash(), Utils.random(0, Integer.MAX_VALUE));
			return true;
		}
		return false;
	}
	
	public boolean click(int option) {
		return click(option, -1);
	}
	
	public boolean clickDialogue(int slotId) {
		if (isOpen()) {
			Actions.menu(Actions.MENU_EXECUTE_DIALOGUE, 0, slotId, getHash(), Utils.random(0, Integer.MAX_VALUE));
			return true;
		}
		return false;
	}
	
	public boolean clickDialogue() {
		return clickDialogue(-1);
	}

	public int getHash() {
		return interfaceId << 16 | componentId;
	}
	
	public boolean isOpen() {
		try {
			return Widgets.getGroupById(interfaceId).getWidgets()[componentId] != null;
		} catch (Exception e) {
			return false;
		}
	}

	public IFSlot[] getSlots() {
		if (getType() != ComponentType.CONTAINER)
			return null;
		try {
			IFSlot[] slots = new IFSlot[Widgets.getGroupById(interfaceId).getWidgets()[componentId].getChildren().length];
			for (int i = 0;i < slots.length;i++)
				slots[i] = new IFSlot(interfaceId, componentId, i);
			return slots;
		} catch (Exception e) {
			return null;
		}
	}

	public String getText() {
		if (getType() != ComponentType.TEXT)
			return null;
		try {
			return Widgets.getGroupById(interfaceId).getWidgets()[componentId].getText();
		} catch (Exception e) {
			return null;
		}
	}
	
	public ComponentType getType() {
		try {
			return ComponentType.forId(Widgets.getGroupById(interfaceId).getWidgets()[componentId].getType());
		} catch (Exception e) {
			return null;
		}
	}
	
	public Item[] getItems() {
		try {
			IFSlot[] slots = getSlots();
			Item[] items = new Item[slots.length];
			for (int i = 0;i < slots.length;i++)
				items[i] = slots[i].getItem();
			return items;
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
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("\tIFComponent: (" + interfaceId + ", " + componentId + ", " + getType() + ")\r\n");
		IFSlot[] slots = getSlots();
		String text = getText();
		if (text != null)
			s.append("\tText: \"" + text + "\"\r\n");
		if (slots != null && slots.length > 0) {
			s.append("\tSlots: \r\n");
			for (IFSlot slot : slots) {
				try {
					s.append(slot.toString());
				} catch (Exception e) {
					
				}
			}
		}
		return s.toString();
	}
}
