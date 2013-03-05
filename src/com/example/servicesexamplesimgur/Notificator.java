package com.example.servicesexamplesimgur;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class Notificator {

	private static final int NOTIFICATION_ID = 1;
	
	private Context context;

	public Notificator(Context context) {
		this.context = context;
	}

	public void notificateWithPendindIntent(Intent intent, String title) {
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, title, when);

		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				intent, 0);
		notification.setLatestEventInfo(context, title, title,
				contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, notification);
	}

}
