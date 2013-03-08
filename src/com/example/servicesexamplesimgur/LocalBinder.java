package com.example.servicesexamplesimgur;

import android.os.Binder;

public class LocalBinder extends Binder {
	
	private BlockingWorker blockingWorker;
	
	public LocalBinder(BlockingWorker blockingWorker) {
		this.blockingWorker = blockingWorker;
	}	

	public BlockingWorker getBlockingWorker() {
		return blockingWorker;
	}
	
}
