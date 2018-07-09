package com.whb.firstservice;

import com.whb.remoteservice.aidl.BookQuery;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.ContentObserver;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class FirstServiceActivity extends Activity implements OnClickListener {
	private static Button bStart = null;
	private static Button bStop = null;
	private static Button bBind = null;
	private static Button bUnbind = null;
	private static Context mContext = null;
	private static final String TAG = "FirstServiceActivity";
	
	private static final String PLAY = "play";
	private static final String STOP = "stop";
	private static final String PAUSE = "pause";
	
	private static final Uri URI = 
			Uri.parse("content://com.hongbowang.mycontentprovider.BookProviderMetaData/books");
	
	private FirstService.MyBinder mFirstServiceBinder;
	
	private BooksContentObserver mContentObserver;
	
	private boolean bindToFirstService = false;
	
	private ServiceConnection mFirstServiceConn = new ServiceConnection() {
		private final String TAG = "mFirstServiceConn";
		@Override
		public void onServiceConnected(ComponentName name, IBinder service){
			Log.d(TAG, "onServiceConnected()...");
			Log.d(TAG, "ComponentName: " + name.toString());
			
			mFirstServiceBinder = (FirstService.MyBinder)service;
		}
		
		@Override
		public void onServiceDisconnected(ComponentName name){
			Log.d(TAG, "onServiceDisconnected()...");
			Log.d(TAG, "ComponentName: " + name.toString());
			mFirstServiceBinder = null;
		}
	};
	
	private class BooksContentObserver extends ContentObserver {

		public BooksContentObserver(Handler handler) {
			super(handler);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onChange(boolean selfChange, Uri uri) {
			// TODO Auto-generated method stub
			Log.d(TAG, "onChange(selfChange:" + selfChange + ", uri: " + uri + ";)");
			Cursor c;
			if(uri != null) {
				c = mContext.getContentResolver().query(uri, null, null, null, "modified DESC");
			} else {
				c = mContext.getContentResolver().query(URI, null, null, null, "modified DESC");
			}
			while(c != null && c.moveToNext()) {
				String ID = c.getString(c.getColumnIndex("_ID"));
				String name = c.getString(c.getColumnIndex("name"));
				String author = c.getString(c.getColumnIndex("author"));
				
				Log.d(TAG, "result: ID: " + ID + " | name: " + name + " | author: " + author);
			}
		}

		@Override
		public void onChange(boolean selfChange) {
			// TODO Auto-generated method stub
			Log.d(TAG, "onChange(selfChange:" + selfChange + ";)");
			Cursor c = mContext.getContentResolver().query(URI, null, null, null, "modified DESC");
			while(c != null && c.moveToNext()) {
				String ID = c.getString(c.getColumnIndex("_ID"));
				String name = c.getString(c.getColumnIndex("name"));
				String author = c.getString(c.getColumnIndex("author"));
				
				Log.d(TAG, "result: ID: " + ID + " | name: " + name + " | author: " + author);
			}
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = getIntent();
		Log.d(TAG, "onCreate()... intent: " + intent);
		mContext = this;
		bStart = (Button)findViewById(R.id.start);
		bStop = (Button)findViewById(R.id.stop);
		bBind = (Button)findViewById(R.id.bind);
		bUnbind = (Button)findViewById(R.id.unbind);
		
		// set the button click listener
		bStart.setOnClickListener(this);
		bStop.setOnClickListener(this);
		bBind.setOnClickListener(this);
		bUnbind.setOnClickListener(this);
		
		mContentObserver = new BooksContentObserver(new Handler());
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onDestroy()...");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onPause()... bindToFirstService: " + bindToFirstService);
		super.onPause();
		// unregister the content observer
		mContext.getContentResolver().unregisterContentObserver(mContentObserver);
		// unbind to query service
		if(bindToFirstService) {
			unbindService(mFirstServiceConn);
			bindToFirstService = false;
		}
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onRestart()...");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onResume()...");
		super.onResume();
		// bind to the RemoteService(BookQueryService)
		// start book query intent 
		// start first service intent
		Intent fsIntent = new Intent();
		fsIntent.setAction("com.whb.firstservice.START_FIRSTSERVICE");
		
		// register the content observer, to observer bookprovider.
		mContext.getContentResolver().registerContentObserver(URI, true, mContentObserver);
		// bind to first service
		if(!bindToFirstService) {
			bindToFirstService = bindService(fsIntent, mFirstServiceConn, BIND_AUTO_CREATE);
		}
		// bind to query book service
		
		Log.d(TAG, "onResume() end... bindToFirstService: " + bindToFirstService);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onStart()...");
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onStop()...");
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		Log.d(TAG, "onCreateOptionsMenu()...");
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onClick()...");
		if(v != null) {
			switch(v.getId()) {
			case R.id.start:
				startServiceDirectly(false);
				break;
				
			case R.id.stop:
				stopServiceDirectly();
				break;
				
			case R.id.bind:
				startServiceByBind();
				break;
				
			case R.id.unbind:
				stopServiceByUnbind();
				break;
				
			default:
				break;
			}
		}
	}
	
	void startServiceDirectly(boolean startDirectly) {
		Log.d(TAG, "startServiceDirectly()...");
		Intent intent = new Intent();
		if(startDirectly) {
			intent.setAction(PLAY);
			intent.setClass(mContext, FirstService.class);
		} else {
			intent.setAction("com.whb.firstservice.START_FIRSTSERVICE");
		}
		
		startService(intent);
	}
	
	void startServiceByBind() {
		Log.d(TAG, "startServiceByBind()...");
		Intent intent = new Intent();
		
		intent.setClass(mContext, FirstService.class);
		intent.setAction(PAUSE);
		
		if(!bindToFirstService) {
			bindToFirstService = bindService(intent, mFirstServiceConn, BIND_AUTO_CREATE);
		}
		
		Log.d(TAG, "startServiceByBind() end... bindToFirstService: " + bindToFirstService);
	}
	
	void stopServiceDirectly() {
		Log.d(TAG, "stopServiceDirectly()...");
		Intent intent = new Intent();
		intent.setClass(mContext, FirstService.class);
		
		stopService(intent);
	}
	
	void stopServiceByUnbind() {
		Log.d(TAG, "stopServiceByUnbind()...");
		
		if(bindToFirstService) {
			unbindService(mFirstServiceConn);
			bindToFirstService = false;
		}
	}
	
	public void getBinderData(View v){
		Log.d(TAG, "stopServiceByUnbind()... mBinder: " + mFirstServiceBinder);
		if(mFirstServiceBinder != null) {
			Toast.makeText(mContext, "Service count is " + mFirstServiceBinder.getCount(), 
					Toast.LENGTH_LONG).show();
		}
	}
}
