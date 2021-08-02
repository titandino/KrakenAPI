package com.darkan.api.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.darkan.cache.def.vars.impl.varbits.VarbitDef;

import kraken.plugin.api.Client;
import kraken.plugin.api.ConVar;
import kraken.plugin.api.Debug;

public class VarManager {

	private static boolean LOADING_VARBITS = false;
	private static boolean MAP_LOADED = false;
	private static boolean CHECKING_VAR_CHANGES = false;
	private static Map<Integer, Set<Integer>> VARBIT_MAP;
	public static final int[] BIT_MASKS = new int[32];

	static {
		int i = 2;
		for (int i2 = 0; i2 < 32; i2++) {
			BIT_MASKS[i2] = i - 1;
			i += i;
		}
	}

	private int[] values;
	private Set<Integer> synced;

	public VarManager() {
		values = new int[10000]; //TODO var max size from defs
		synced = new HashSet<>();
	}
	
	public void setVar(int id, int value) {
		if (MAP_LOADED) {
			for (VarbitDef def : getVarbits(id)) {
				int prev = getVarbitForValue(def, values[id]);
				int curr = getVarbitForValue(def, value);
				if (prev != curr)
					System.out.println("Varbit changed: " + def.id + " from " + prev + " -> " + curr);
			}
		}
		values[id] = value;
	}
	
	public void setVarBit(int id, int value) {
		VarbitDef defs = VarbitDef.get(id);
		int mask = BIT_MASKS[defs.endBit - defs.startBit];
		if (value < 0 || value > mask) {
			value = 0;
		}
		mask <<= defs.startBit;
		int varpValue = (getVar(defs.baseVar) & (mask ^ 0xffffffff) | value << defs.startBit & mask);
		if (varpValue != getVar(defs.baseVar)) {
			setVar(defs.baseVar, varpValue);
		}
	}

	public int getVar(int id) {
		if (!synced.contains(id)) {
			ConVar var = Client.getConVarById(id);
			if (var != null)
				setVar(id, var.getValue());
			synced.add(id);
		}
		return values[id];
	}
	
	public int forceGetVar(int id) {
		ConVar var = Client.getConVarById(id);
		if (var != null)
			setVar(id, var.getValue());
		return values[id];
	}
	
	public void checkVarUpdates() {
		if (CHECKING_VAR_CHANGES)
			return;
		CHECKING_VAR_CHANGES = true;
		Thread checker = new Thread(() -> {
			Debug.log("Checking var changes...");
			//for (int i = 0;i < values.length;i++) {
			for (int i : Arrays.asList(6, 3513, 3913, 3914, 3915, 4876, 6091)) {
				int prv = values[i];
				int curr = forceGetVar(i);
				if (prv != curr)
					Debug.log("Var changed: " + i + " from " + prv + " -> " + curr);
			}
			Debug.log("Done checking var changes...");
			CHECKING_VAR_CHANGES = false;
		});
		checker.setPriority(Thread.MAX_PRIORITY);
		checker.start();
	}
	
	public static int getVarbitForValue(VarbitDef defs, int value) {
		return value >> defs.startBit & BIT_MASKS[defs.endBit - defs.startBit];
	}
	
	public int getVarBit(int id) {
		VarbitDef defs = VarbitDef.get(id);
		if (defs == null)
			return 0;
		return getVarbitForValue(defs, getVar(defs.baseVar));
	}
	
	public boolean bitFlagged(int id, int bit) {
		return (getVar(id) & (1 << bit)) != 0;
	}
	
	public List<VarbitDef> getVarbits(int varId) {
		List<VarbitDef> vbDefs = new ArrayList<>();
		Set<Integer> vbIds = VARBIT_MAP.get(varId);
		if (vbIds == null || vbIds.size() <= 0)
			return vbDefs;
		for (int vbId : vbIds)
			vbDefs.add(VarbitDef.get(vbId));
		return vbDefs;
	}
	
	public static void linkVarbits() {
		LOADING_VARBITS = true;
		new Thread(() -> {
			VARBIT_MAP = new HashMap<>();
			int max = VarbitDef.getParser().getMaxId();
			int prev = 0;
			Debug.log("Loading varbit analyzer data... (0%)");
			for (int i = 0;i < VarbitDef.getParser().getMaxId();i++) {
				int perc = (int) (((double) i / (double) max) * 100.0);
				if (perc != prev && perc % 10 == 0) {
					prev = perc;
					Debug.log("Loading varbit analyzer data... (" + perc + "%)");
				}
				VarbitDef def = VarbitDef.get(i);
				if (def == null)
					continue;
				Set<Integer> vars = VARBIT_MAP.get(def.baseVar);
				if (vars == null)
					vars = new HashSet<>();
				vars.add(i);
				VARBIT_MAP.put(def.baseVar, vars);
			}
			Debug.log("Varbit analyzer data loaded from cache...");
			MAP_LOADED = true;
		}).start();
	}

	public void clearSynced() {
		if (VARBIT_MAP == null || !LOADING_VARBITS)
			linkVarbits();
		synced.clear();
	}
}
