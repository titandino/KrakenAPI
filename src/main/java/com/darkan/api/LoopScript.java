package com.darkan.api;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.darkan.Constants;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.util.Logger;
import com.darkan.api.util.Utils;

import kraken.plugin.api.*;

public abstract class LoopScript {

	private String name;
	private long started = 0;
	private Map<Integer, Integer> startXps = new HashMap<>();
	private String state = "Initializing...";
	private String error = "";
	private boolean enabled = false;

	private long lastMyPlayerAnim;
	private long lastMyPlayerMoved;

	private long loopTimer;
	private long lastLoop;

	protected int loopDelay;
	private int currDelay;

	private long nextRun;

	private Supplier<Boolean> sleepConstraint;
	private long sleepWhileMax = -1;
	private long sleepWhileMin = -1;
	private int sleepWhileExtra;

	private int localPlayerAtt = 0;

	public LoopScript(String name) {
		this(name, 600);
	}

	public LoopScript(String name, int loopDelay) {
		this.name = name;
		this.loopDelay = loopDelay;
	}

	public abstract boolean onStart();

	protected abstract void loop();

	public abstract void paintImGui(long runtime);

	public abstract void paintOverlay(long runtime);

	public abstract void onStop();

	protected final int onLoop() {
		try {
			Player self = Players.self();
			if (self == null) {
				state = "Finding local player... " + localPlayerAtt++;
				return 0;
			}
			MyPlayer.set(self);
			if (!enabled) {
				if (Client.getStatById(Client.HITPOINTS).getXp() <= 100)
					return 0;
				MyPlayer.getVars().clearSynced();
				for (int i = 0; i < Constants.SKILL_NAME.length; i++)
					startXps.put(i, Client.getStatById(i).getXp());
				boolean started = onStart();
				if (started) {
					start();
					enabled = true;
				}
				return 0;
			}

			if (self.isAnimationPlaying())
				lastMyPlayerAnim = System.currentTimeMillis();
			if (self.isMoving())
				lastMyPlayerMoved = System.currentTimeMillis();
			localPlayerAtt = 0;

			if (sleepConstraint != null) {
				if (System.currentTimeMillis() >= sleepWhileMin && (!sleepConstraint.get() || System.currentTimeMillis() >= sleepWhileMax))
					sleepConstraint = null;
				return loopDelay + sleepWhileExtra;
			}

			if (nextRun < System.currentTimeMillis()) {
				currDelay = loopDelay;
				loop();
				nextRun = System.currentTimeMillis() + Utils.gaussian(currDelay, currDelay / 2);
			}
		} catch (Exception e) {
			error = e.toString();
			Logger.handle(e);
			return Utils.gaussian(2000, 1000);
		}
		return 10;
	}

	public long getTimeSinceLastAnimation() {
		return System.currentTimeMillis() - lastMyPlayerAnim;
	}

	public long getTimeSinceLastMoving() {
		return System.currentTimeMillis() - lastMyPlayerMoved;
	}

	public void sleepWhile(long maxTime, Supplier<Boolean> constraint) {
		sleepWhile(-1, maxTime, constraint);
	}

	public void sleepWhile(long minTime, long maxTime, Supplier<Boolean> constraint) {
		sleepConstraint = constraint;
		sleepWhileMin = System.currentTimeMillis() + minTime;
		sleepWhileMax = System.currentTimeMillis() + maxTime;
		sleepWhileExtra = 0;
	}

	public void sleepWhile(long minTime, long maxTime, int extraDelay, Supplier<Boolean> constraint) {
		sleepWhileMin = System.currentTimeMillis() + minTime;
		sleepWhileMax = System.currentTimeMillis() + maxTime;
		sleepWhileExtra = extraDelay;
		sleepConstraint = constraint;
	}

	public void printGenericXpGain(long runtime) {
		for (int skillId : startXps.keySet()) {
			int gainedXp = Client.getStatById(skillId).getXp() - startXps.get(skillId);
			if (gainedXp > 1)
				ImGui.label(Constants.SKILL_NAME[skillId] + " XP p/h: " + Time.perHour(runtime, gainedXp));
		}
	}

	public void onPaint() {
		long runtime = System.currentTimeMillis() - started;
		ImGui.label(name + " - " + Time.formatTime(runtime));
		ImGui.label(state);
		ImGui.label(error);
		paintImGui(runtime);
	}

	public void onPaintOverlay() {
		long runtime = System.currentTimeMillis() - started;
		paintOverlay(runtime);
	}

	public final void setState(String state) {
		this.state = state;
	}

	public final void sleep(int ms) {
		currDelay += Utils.gaussian(ms, ms / 2);
	}

	public final void start() {
		started = System.currentTimeMillis();
	}

	public void stop() {
		onStop();
	}

	public final void process() {
		long currTime = System.currentTimeMillis();
		loopTimer -= (currTime - lastLoop);
		lastLoop = currTime;
		if (loopTimer <= 0)
			loopTimer = onLoop();
	}

	public void onVarChange() {

	}
}
