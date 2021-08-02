package com.darkan.api.util;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
	
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
	private static final Calendar CALENDAR = Calendar.getInstance();

	public static String getDateString() {
		return DATE_FORMAT.format(CALENDAR.getTime());
	}
	
	public static void handle(Throwable throwable) {
		logError(throwable);
		System.err.println("ERROR! THREAD NAME: " + Thread.currentThread().getName());
		throwable.printStackTrace();
	}

	public static void log(Object classInstance, Object message) {
		log(classInstance.getClass().getSimpleName(), message);
	}

	public static void log(String className, Object message) {
		String text = "[" + className + "]" + " " + message.toString();
		System.out.println(text);
	}
	
	public static void writeToFile(String fileName, String text) {
		try {
			String[] parts = fileName.split("/");

			File file;
			for (int i = 0; i < parts.length - 1; ++i) {
				file = new File(System.getProperty("user.home")+"/.freedev/" + parts[i]);
				if (!file.exists()) {
					file.mkdir();
				}
			}
			FileWriter writer = new FileWriter(System.getProperty("user.home")+"/.freedev/" + fileName, true);
			writer.write("[" + getDateString() + "]:  "+ text + "\r\n");
			writer.close();
		} catch (Exception e) {

		}
	}

	public static void logError(Throwable throwable) {
		StringWriter errors = new StringWriter();
		throwable.printStackTrace(new PrintWriter(errors));
		writeToFile("errorLog.log", Thread.currentThread().getName() + ": " + errors.toString());
	}

	public static void logError(String string) {
		writeToFile("errorLog.log", string);
	}

}
