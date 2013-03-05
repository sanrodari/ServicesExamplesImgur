package com.example.servicesexamplesimgur;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
		Log.i("SEI", "some");
	}
	
}
