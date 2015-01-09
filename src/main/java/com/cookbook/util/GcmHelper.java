package com.cookbook.util;

import java.io.IOException;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;

public class GcmHelper {

	private GcmHelper() {
		// forbid instantiation
	}

	public static boolean sendNotification(String userGcmId, String type, String title, String message) {
		Message.Builder messageBuilder = new Message.Builder();
		messageBuilder.addData("type", type);
		messageBuilder.addData("title", title);
		messageBuilder.addData("message", message);

		Message gcmMessage = messageBuilder.build();
		Sender sender = new Sender(GcmConstants.GSM_API_KEY);
		try {
			sender.send(gcmMessage, userGcmId, 5);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
