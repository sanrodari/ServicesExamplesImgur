package com.example.servicesexamplesimgur;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void doIntentService(View v) {
		Intent intent = new Intent(this, IntentServiceExample.class);
		startService(intent);
	}
	
	public void doService(View v) {
		Intent intent = new Intent(this, ServiceExample.class);
		startService(intent);
	}

	public void doBindService(View v) {
		Intent intent = new Intent(this, BoundService.class);
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
	}
	
	private void downloadImage(BlockingWorker blockingWorker) {
		
		new Thread(new Runnable() {
			public void run() {
				
				boolean success = false;
				for (int i = 0; i < 5 && !success; i++) {
					Log.i(Constants.TAG, String.format("Intento %s", i + 1));
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
						
						new Notificator(MainActivity.this).notificateWithPendindIntent(notificationIntent, 
							chosenImage.getString("title"));
						success = true;
						
						unbindService(serviceConnection);
					} catch (Exception e) {
						e.printStackTrace();
						Log.d(Constants.TAG, "Error al tratar de descargar la imagen");
					}
				}
				
				if(!success) {
					new Notificator(MainActivity.this).notificateWithPendindIntent(null, 
						"Error al descargar la imagen.");
				}
			}

		}).start();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		try {
			unbindService(serviceConnection);
		} catch (Exception e) {
			Log.i(Constants.TAG, "Servicio desligado");
		}
	}
	
	private ServiceConnection serviceConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName name, IBinder service) {
			LocalBinder localBinder = (LocalBinder) service;
			downloadImage(localBinder.getBlockingWorker());
		}
		public void onServiceDisconnected(ComponentName name) {	}
	};
	
}
