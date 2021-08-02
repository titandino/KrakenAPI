package com.darkan.api.pathing.action;

import java.util.function.Supplier;

import com.darkan.api.State;
import com.darkan.api.StateMachineScript;
import com.darkan.api.pathing.action.node.TraversalNodeList;

public class Traversal extends State {
	
	private State next;
	private TraversalProcessor processor;
	
	public Traversal(State next, Supplier<Boolean> finishedCondition, TraversalNodeList nodes) {
		this.next = next;
		this.processor = new TraversalProcessor(finishedCondition, nodes);
	}

	@Override
	public State checkNext() {
		if (!processor.process())
			return next;
		return null;
	}

	@Override
	public void loop(StateMachineScript ctx) {

	}

}
