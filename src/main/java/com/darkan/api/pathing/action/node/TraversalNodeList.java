package com.darkan.api.pathing.action.node;

public class TraversalNodeList {
	
	private TraversalNode head;
	private TraversalNode curr;

	public TraversalNodeList(TraversalNode start) {
		this.head = start;
		this.curr = start;
	}
	
	public TraversalNodeList add(TraversalNode node) {
		this.curr.setNext(node);
		node.setPrev(this.curr);
		this.curr = node;
		return this;
	}

	public TraversalNode getHead() {
		return head;
	}
	
	public TraversalNodeList copy() {
		TraversalNodeList copy = new TraversalNodeList(this.head.copy());
		copy.curr = copy.head;
		while(copy.curr.next != null) {
			copy.curr.next = copy.curr.next.copy();
			copy.curr = copy.curr.next;
		}
		copy.curr = copy.head;
		return copy;
	}
}
