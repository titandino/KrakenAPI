package com.darkan.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DataUpdateThreadFactory implements ThreadFactory {

	private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
	private final ThreadGroup group;
	private final AtomicInteger threadNum = new AtomicInteger(1);
	private final String namePrefix;
	
	public DataUpdateThreadFactory() {
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		namePrefix = "Data Update Pool - " + POOL_NUMBER.getAndIncrement() + "-Thread-";
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, namePrefix + threadNum.getAndIncrement(), 0);
		if (t.isDaemon())
			t.setDaemon(false);
		if (t.getPriority() != Thread.MAX_PRIORITY)
			t.setPriority(Thread.MAX_PRIORITY);
		return t;
	}
	
}
