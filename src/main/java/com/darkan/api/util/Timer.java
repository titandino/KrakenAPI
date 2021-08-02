package com.darkan.api.util;

public class Timer {
	
	private long lastPress = 0;
	
	public void click() {
		lastPress = System.currentTimeMillis();
	}
	
	public long time() {
		return System.currentTimeMillis() - lastPress;
	}

}
