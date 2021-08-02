package com.darkan.api.pathing.action;

import java.util.List;
import java.util.function.Supplier;

import com.darkan.api.pathing.FixedTileStrategy;
import com.darkan.api.pathing.LocalPathing;
import com.darkan.api.pathing.action.node.TraversalNode;
import com.darkan.api.pathing.action.node.TraversalNodeList;
import com.darkan.api.world.WorldTile;

public class TraversalProcessor {
	
	private Supplier<Boolean> finishedCondition;
	private TraversalNodeList nodes;
	private TraversalNode curr;
	
	public TraversalProcessor(Supplier<Boolean> finishedCondition, TraversalNodeList nodes) {
		this.finishedCondition = finishedCondition;
		this.nodes = nodes.copy();
		this.curr = this.nodes.getHead();
	}

	public boolean process() {
		if (finishedCondition.get())
			return false;
		if (curr.process())
			return true;
		else if (curr.getNext() != null) {
			curr = curr.getNext();
			return true;
		} else
			return false;
	}

	public TraversalNodeList getNodes() {
		return nodes;
	}
	
	public static void main(String[] args) {
		List<WorldTile> path = LocalPathing.findLocalRoute(new WorldTile(3187, 3425, 0), 1, new FixedTileStrategy(new WorldTile(3182, 3371, 0)), false);
		for (WorldTile tile : path)
			System.out.println(tile);
	}
}
