package com.darkan.api.accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.darkan.api.util.Utils;
import com.darkan.api.world.SpotAnim;
import com.darkan.api.world.WorldTile;

import kraken.plugin.api.Effect;
import kraken.plugin.api.Effects;
import kraken.plugin.api.Filter;
import kraken.plugin.api.Players;

public class SpotAnims {
	
	private static List<SpotAnim> SPOT_ANIMS = new CopyOnWriteArrayList<>();
	
	public static void update() {
		try {
			List<SpotAnim> list = new ArrayList<>();
			for (Effect sa : Effects.all()) {
				if (sa == null)
					continue;
				SpotAnim spotAnim = new SpotAnim(sa);
				list.add(spotAnim);
			};
			SPOT_ANIMS.clear();
			SPOT_ANIMS.addAll(list);
		} catch(Exception e) {
			
		}
	}
	
	public static SpotAnim getClosest(Filter<SpotAnim> filter) {
		Map<Integer, SpotAnim> distanceMap = new TreeMap<Integer, SpotAnim>();
		List<SpotAnim> spotAnims = getNearby(filter);
		WorldTile pTile = new WorldTile(Players.self().getGlobalPosition());
		for (SpotAnim spotAnim : spotAnims) {
			if (spotAnim != null) {
				int distance = Utils.getDistanceTo(pTile, spotAnim.getPosition());
				if (distance != -1)
					distanceMap.put(distance, spotAnim);
			}
		}
		if (distanceMap.isEmpty())
			return null;
		List<Integer> sortedKeys = new ArrayList<Integer>(distanceMap.keySet());
		Collections.sort(sortedKeys);
		return distanceMap.get(sortedKeys.get(0));
	}

	public static SpotAnim getClosestReachable(Filter<SpotAnim> filter) {
		Map<Integer, SpotAnim> distanceMap = new TreeMap<Integer, SpotAnim>();
		List<SpotAnim> spotAnims = getNearby(filter);
		WorldTile pTile = new WorldTile(Players.self().getGlobalPosition());
		for (SpotAnim spotAnim : spotAnims) {
			if (spotAnim != null) {
				int distance = Utils.getRouteDistanceTo(pTile, spotAnim.getPosition());
				if (distance != -1)
					distanceMap.put(distance, spotAnim);
			}
		}
		if (distanceMap.isEmpty())
			return null;
		List<Integer> sortedKeys = new ArrayList<Integer>(distanceMap.keySet());
		Collections.sort(sortedKeys);
		return distanceMap.get(sortedKeys.get(0));
	}
	
 	public static List<SpotAnim> getNearby(Filter<SpotAnim> filter) {
		List<SpotAnim> list = new ArrayList<>();
		for (SpotAnim spotAnim : SPOT_ANIMS) {
			if (filter == null || filter.accept(spotAnim))
				list.add(spotAnim);
		}
		return list;
	}
	
	public static List<SpotAnim> getNearby() {
		return getNearby(null);
	}
	
	public static List<SpotAnim> getNearbyReachable() {
		List<SpotAnim> reachable = new ArrayList<>();
		List<SpotAnim> spotAnims = getNearby();
		WorldTile pTile = new WorldTile(Players.self().getGlobalPosition());
		for (SpotAnim spotAnim : spotAnims) {
			if (spotAnim != null) {
				int distance = Utils.getRouteDistanceTo(pTile, spotAnim.getPosition());
				if (distance != -1)
					reachable.add(spotAnim);
			}
		}
		return reachable;
	}
	
	public static List<SpotAnim> getOrderedClosest(Filter<SpotAnim> filter) {
		Map<Integer, List<SpotAnim>> distanceMap = new HashMap<>();
		List<SpotAnim> closest = new ArrayList<>();
		List<SpotAnim> spotAnims = getNearby(filter);
		WorldTile pTile = new WorldTile(Players.self().getGlobalPosition());
		for (SpotAnim spotAnim : spotAnims) {
			if (spotAnim != null) {
				int distance = Utils.getRouteDistanceTo(pTile, spotAnim.getPosition());
				if (distance != -1) {
					List<SpotAnim> spotAnimAtDist = distanceMap.get(distance);
					if (spotAnimAtDist == null)
						spotAnimAtDist = new ArrayList<>();
					spotAnimAtDist.add(spotAnim);
					distanceMap.put(distance, spotAnimAtDist);
				}
			}
		}
		if (distanceMap.isEmpty())
			return closest;
		List<Integer> sortedKeys = new ArrayList<Integer>(distanceMap.keySet());
		Collections.sort(sortedKeys);
		for (int key : sortedKeys)
			closest.addAll(distanceMap.get(key));
		return closest;
	}
	
	public static List<SpotAnim> getOrderedClosest() {
		return getOrderedClosest(null);
	}
}
