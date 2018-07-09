package com.whb.firstservice;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class FirstService extends Service {
	private static final String TAG = "FirstService";
	
	private int count;
	private MyBinder mBinder = new MyBinder();
	private boolean quit;
	
	public class MyBinder extends Binder {
		public int getCount(){
			Log.d(TAG, "getCount()... count: " + count);
			return count;
		}
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		String action = arg0.getAction();
		Log.d(TAG, "onBind()... action: " + action);
		return mBinder;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onConfigurationChanged()...");
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d(TAG, "onCreate()... quit: " + quit);
		new Thread() {
			@Override
			public void run() {
				while (!quit) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						
					}
					count++;
				}
			}
		}.start();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		quit = true;
		Log.d(TAG, "onDestroy()...");
	}

	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub
		super.onRebind(intent);
		String action = intent.getAction();
		Log.d(TAG, "onRebind()... action: " + action);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		//super.onStart(intent, startId);
		String action = intent.getAction();
		Log.d(TAG, "onStart()... action: " + action);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		Log.d(TAG, "onStartCommand()... action: " + action);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		Log.d(TAG, "onUnbind()... action: " + action);
		return super.onUnbind(intent);
	}
	
}