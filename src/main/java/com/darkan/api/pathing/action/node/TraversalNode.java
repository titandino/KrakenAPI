package com.darkan.api.pathing.action.node;

public abstract class TraversalNode {
	
	protected TraversalNode prev;
	protected TraversalNode next;
	
	public abstract boolean canStart();
	public abstract boolean process();
	public abstract boolean reached();
	public abstract TraversalNode copy();
	
	public TraversalNode getPrev() {
		return prev;
	}
	
	public void setPrev(TraversalNode prev) {
		this.prev = prev;
	}
	
	public TraversalNode getNext() {
		return next;
	}
	
	public void setNext(TraversalNode next) {
		this.next = next;
	}
	
}
