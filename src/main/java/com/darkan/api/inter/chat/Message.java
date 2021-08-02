package com.darkan.api.inter.chat;

public class Message {
	
	private static final String TS_START = "<col=ffffff>[<col=7fa9ff>";
	private static final String TS_END = "<col=ffffff>]</col> ";
	
	private static final String NAME_START = "<col=ffffff>";
	private static final String NAME_END = ":</col> ";
	
	private static final String COLOR_START = "<col=";
	private static final String COLOR_END = ">";
	
	private String timestamp = "";
	private String color = "FFFFFF";
	private String playerName = "";
	private String text = "";
	
	public Message(String whole) {
		parse(whole);
	}
	
	public void parse(String whole) {
		String orig = new StringBuffer(whole).toString();
		try {
			if (whole.startsWith(TS_START)) {
				timestamp = whole.substring(whole.indexOf(TS_START) + TS_START.length(), whole.indexOf(TS_END));
				whole = whole.substring(whole.indexOf(TS_END) + TS_END.length());
			}
			if (whole.startsWith(NAME_START)) {
				playerName = whole.substring(whole.indexOf(NAME_START) + NAME_START.length(), whole.indexOf(NAME_END));
				whole = whole.substring(whole.indexOf(NAME_END) + NAME_END.length());
			}
			if (whole.startsWith(COLOR_START)) {
				color = whole.substring(whole.indexOf(COLOR_START) + COLOR_START.length(), whole.indexOf(COLOR_END)).toUpperCase();
				whole = whole.substring(whole.indexOf(COLOR_END) + COLOR_END.length()).replace("</col>", "");
			}
			text = whole;
		} catch (Exception e) {
			System.err.println("Error parsing chat message: " + orig);
			e.printStackTrace();
		}
	}
	
	public String getTimestamp() {
		return timestamp;
	}

	public String getColor() {
		return color;
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getText() {
		return text;
	}
	
	public boolean isChat() {
		return !playerName.isEmpty() && color.equals("7FA9FF");
	}
	
	public boolean isGame() {
		return !isChat();
	}
	
	@Override
	public String toString() {
		return "[" + timestamp + " -> (" + color + ")" + playerName + " -> " + text + "]";
	}
}
