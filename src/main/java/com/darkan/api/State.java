package com.darkan.api;

public abstract class State {
	public abstract State checkNext();
	public abstract void loop(StateMachineScript ctx);
}
