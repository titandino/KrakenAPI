package com.darkan.api.item;

import com.darkan.api.entity.NPC;
import com.darkan.api.inter.IFSlot;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.util.Utils;
import com.darkan.api.world.WorldObject;
import com.darkan.cache.def.items.ItemDef;

import kraken.plugin.api.Actions;
import kraken.plugin.api.Debug;

public class Item {
	
	private IFSlot slot;
	private int id;
	private int amount;
	
	public Item(IFSlot slot, int id, int amount) {
		this.slot = slot;
		this.id = id;
		this.amount = amount;
	}
	
	public Item(int id) {
		this(id, 1);
	}
	
	public Item(int id, int amount) {
		this.id = id;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public void click(int option) {
		slot.click(option);
	}
	
	public boolean useOn(Item target) {
		if (target != null && slot.isOpen()) {
			Actions.menu(Actions.MENU_EXECUTE_SELECTABLE_WIDGET, 0, slot.getSlotId(), slot.getHash(), Utils.random(0, Integer.MAX_VALUE));
			Actions.menu(Actions.MENU_EXECUTE_SELECT_WIDGET_ITEM, 0, target.slot.getSlotId(), target.slot.getHash(), Utils.random(0, Integer.MAX_VALUE));
			return true;
		}
		return false;
	}
	
	public boolean useOn(NPC target) {
		if (target != null && slot.isOpen()) {
			Actions.menu(Actions.MENU_EXECUTE_SELECTABLE_WIDGET, 0, slot.getSlotId(), slot.getHash(), Utils.random(0, Integer.MAX_VALUE));
			Actions.menu(Actions.MENU_EXECUTE_SELECT_NPC, target.getServerIndex(), 0, 0, Utils.random(0, Integer.MAX_VALUE));
			return true;
		}
		return false;
	}
	
	public boolean useOn(WorldObject target) {
		if (target != null && slot.isOpen()) {
			Actions.menu(Actions.MENU_EXECUTE_SELECTABLE_WIDGET, 0, slot.getSlotId(), slot.getHash(), Utils.random(0, Integer.MAX_VALUE));
			Actions.menu(Actions.MENU_EXECUTE_SELECT_OBJECT, target.getId(), target.getX(), target.getY(), Utils.random(0, Integer.MAX_VALUE));
			return true;
		}
		return false;
	}
	
	public boolean useOn(GroundItem target) {
		if (target != null && slot.isOpen()) {
			Actions.menu(Actions.MENU_EXECUTE_SELECTABLE_WIDGET, 0, slot.getSlotId(), slot.getHash(), Utils.random(0, Integer.MAX_VALUE));
			Actions.menu(Actions.MENU_EXECUTE_SELECT_GROUND_ITEM, target.getId(), target.getPosition().getX(), target.getPosition().getY(), Utils.random(0, Integer.MAX_VALUE));
			return true;
		}
		return false;
	}
	
	public void click(String option) {
		int op = -1;
		if (slot.getInterfaceId() == Interfaces.getEquipment().getInterfaceId()) {
			op = getDef().getEquipOpIdForName(option);
			op += 2;
		}
		if (slot.getInterfaceId() == Interfaces.getInventory().getInterfaceId()) {
			op = getDef().getInvOpIdForName(option);
			Debug.log("invent option: " + op);
			op = switch(op) {
				case 0 -> 1;
				case 1 -> 2;
				case 2 -> 3;
				case 3 -> 7;
				case 4 -> 8;
				default -> -1;
			};
		}
        if (slot.getInterfaceId() == Interfaces.getBankInventory().getInterfaceId()) {
            op = switch(option) {
                case "Deposit-1" -> 2;
                case "Deposit-5" -> 3;
                case "Deposit-10" -> 4;
                case "Deposit-50" -> 5;
                case "Deposit-X" -> 6;
                case "Deposit-All" -> 7;
                default -> 1;
            };
        }
        if (slot.getInterfaceId() == Interfaces.getDepositBox().getInterfaceId()) {
            op = switch(option) {
                case "Deposit-1" -> 2;
                case "Deposit-5" -> 3;
                case "Deposit-10" -> 4;
                case "Deposit-All" -> 5;
                case "Deposit-X" -> 6;
                default -> 1;
            };
        }
        if (slot.getInterfaceId() == Interfaces.getBank().getInterfaceId()) {
            op = switch(option) {
                case "Withdraw-1" -> 1;
                case "Withdraw-5" -> 3;
                case "Withdraw-10" -> 4;
                case "Withdraw-All" -> 7;
                case "Withdraw-X" -> 6;
                case "Withdraw-Placeholder" -> 8;
                default -> 1;
            };
        }
		if (op != -1)
			click(op);
	}	
	
	public ItemDef getDef() {
		return ItemDef.get(id);
	}
	
	public String getName() {
		return getDef().name;
	}
	
	@Override
	public String toString() {
		return "[" + id + "("+getName()+"), " + amount + "]";
	}
}
