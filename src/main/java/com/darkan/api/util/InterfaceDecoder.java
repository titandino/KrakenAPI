package com.darkan.api.util;

import java.io.IOException;

public class InterfaceDecoder {
	
	public static void main(String[] args) throws IOException {
		int hash = 93716496;
		int interfaceId = hash >> 16;
		int slotId = (hash & 0xFF);
		int childId = hash - (interfaceId << 16);
		
		System.out.println("Interface: " + interfaceId + ", child: " + childId + ", slot: " + slotId);
		System.out.println("Encoded matches: " + (interfaceId << 16 | childId) + " - " + hash);
	}

}
