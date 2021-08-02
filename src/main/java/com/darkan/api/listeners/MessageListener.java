package com.darkan.api.listeners;

import com.darkan.api.inter.chat.Message;

public interface MessageListener {

	void onMessageReceived(Message message);
}
