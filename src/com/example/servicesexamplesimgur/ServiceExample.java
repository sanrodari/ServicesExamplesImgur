package com.example.servicesexamplesimgur;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class ServiceExample extends Service {

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		new Thread(new Runnable() {
			public void run() {
				BlockingWorker worker = new BlockingWorker();
				String response = worker.performQuery();
				
				try {
					JSONObject jsonResponse = new JSONObject(response);
					JSONArray data = jsonResponse.getJSONArray("data");
					
					// data[random].title
					// data[random].link
					Random random = new Random();
					int randomIndex = random.nextInt(data.length());
					
					JSONObject chosenImage = data.getJSONObject(randomIndex);
					String path = worker.download(chosenImage.getString("link"),
							chosenImage.getString("id"));
					
					Log.i(Constants.TAG, "File:" + path);
					
					Intent notificationIntent = new Intent();
					notificationIntent.setAction(Intent.ACTION_VIEW);
					notificationIntent.setDataAndType(Uri.parse("file://" + path), "image/*");
					
					new Notificator(ServiceExample.this).notificateWithPendindIntent(notificationIntent, 
						chosenImage.getString("title"));
				} catch (Exception e) {
					e.printStackTrace();
					Log.d(Constants.TAG, "Error al tratar de descargar la imagen");
				}
			}
		}).start();
		
		return Service.START_NOT_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
