package com.darkan.thread;

import com.darkan.api.accessors.GroundItems;
import com.darkan.api.accessors.NPCs;
import com.darkan.api.accessors.SpotAnims;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.inter.chat.Chatbox;
import com.darkan.api.util.Logger;

public class DataUpdateThread extends Thread {

	public static volatile boolean UPDATING_DATA = false;
	
	public DataUpdateThread() {
		setPriority(Thread.MAX_PRIORITY);
		setName("Data Update Thread");
		this.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable ex) {
				Logger.handle(ex);
			}
		});
	}
	
	@Override
	public void run() {
		try {
			if (UPDATING_DATA)
				return;
			UPDATING_DATA = true;
			Chatbox.update();
			NPCs.update();
			WorldObjects.update();
			SpotAnims.update();
			GroundItems.update();
			UPDATING_DATA = false;
		} catch(Exception e) {
			Logger.handle(e);
		}
	}
}
