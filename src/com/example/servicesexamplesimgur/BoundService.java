package com.example.servicesexamplesimgur;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BoundService extends Service {
	
	private LocalBinder localBinder = new LocalBinder(new BlockingWorker());

	@Override
	public IBinder onBind(Intent intent) {
		return localBinder;
	}

}
