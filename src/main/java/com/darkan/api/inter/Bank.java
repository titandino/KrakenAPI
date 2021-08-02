package com.darkan.api.inter;

public class Bank {
	
	public static boolean isOpen() {
		return kraken.plugin.api.Bank.isOpen();
	}
	
	public static boolean depositInventory() {
		return Interfaces.click(1, 517, 39, -1);
	}
	
	public static boolean depositEquipment() {
		return Interfaces.click(1, 517, 42, -1);
	}
	
	public static boolean depositBoB() {
		return Interfaces.click(1, 517, 45, -1);
	}
	
	public static boolean loadPreset(int presetNum) {
		return Interfaces.click(1, 517, 116, presetNum);
	}

}
