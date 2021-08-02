package com.darkan.api.inter.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.IFComponent;
import com.darkan.api.inter.IFSlot;

public class Chatbox {
	
	private static List<String> CHATS = new CopyOnWriteArrayList<>();
	private static IFComponent CHATBOX = new IFComponent(137, 85);
	private static IFComponent CHATBOX2 = new IFComponent(1472, 193);
	private static IFSlot HEALTH_BAR = new IFSlot(1430, 7, 8);
	
	public static void update() {
		try {
			List<String> list = new ArrayList<>();
			for (IFSlot slot : CHATBOX.isOpen() ? CHATBOX.getSlots() : CHATBOX2.getSlots())
				list.add(slot.getText());
			CHATS.clear();
			CHATS.addAll(list);
			String healthStr = HEALTH_BAR.getText();
			if (!healthStr.isEmpty()) {
				String[] values = healthStr.split("/");
				MyPlayer.setHealth(Integer.valueOf(values[0]));
				MyPlayer.setMaxHealth(Integer.valueOf(values[1]));
			}
		} catch(Exception e) {
			
		}
	}
	
	public static List<String> getMessages() {
		return CHATS;
	}

}
