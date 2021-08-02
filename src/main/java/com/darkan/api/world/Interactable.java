package com.darkan.api.world;

public interface Interactable {
	String name();
	boolean hasOption(String string);
	boolean interact(int option);
	boolean interact(String action);
}
