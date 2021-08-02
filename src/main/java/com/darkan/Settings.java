package com.darkan;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import com.darkan.api.util.JsonFileManager;
import com.google.gson.JsonIOException;

public final class Settings {
	
	private static Settings SETTINGS;
	private static Settings DEFAULTS = new Settings();
	
	public static Settings getConfig() {
		if (SETTINGS == null)
			loadConfig();
		return SETTINGS;
	}

	private String cachePath;
	private boolean debug = false;
	
	public Settings() {
		this.cachePath = "C:/ProgramData/Jagex/RuneScape/";
		this.debug = false;
	}	

	public static void loadConfig() {
		try {
			File configFile = new File(System.getProperty("user.home")+"/.freedev/config.json");
			if (configFile.exists())
				SETTINGS = JsonFileManager.loadJsonFile(new File(System.getProperty("user.home")+"/.freedev/config.json"), Settings.class);
			else
				SETTINGS = new Settings();
			for (Field f : SETTINGS.getClass().getDeclaredFields()) {
			    if (f.get(SETTINGS) == null)
			    	f.set(SETTINGS, f.get(DEFAULTS));
			}
			JsonFileManager.saveJsonFile(SETTINGS, configFile);
		} catch (JsonIOException | IOException | IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
			System.exit(5);
		}
	}

	public boolean isDebug() {
		return debug;
	}

	public String getCachePath() {
		return cachePath != null ? cachePath : DEFAULTS.cachePath;
	}
}
