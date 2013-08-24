package com.whp.android;


import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


/*
 * Description	: 
 */
public class BasicApplication extends Application {

	
	private static final String TAG = BasicApplication.class.getCanonicalName();

	
	public static String APPLICATION_STATUS_CHANGED		= "com.whp.application.statuschanged";
	public static String APPLICATION_STATUS				= "com.whp.application.status";
	public static String APPLICATION_STATUS_ACTIVE		= "ACTIVE";
	public static String APPLICATION_STATUS_NOT_ACTIVE	= "NOT_ACTIVE";

	
	private static Context context;
	private static int activitiesRunning = 0;
	
	public void onCreate() {
		Log.d(TAG, "onCreate");
		super.onCreate();
		BasicApplication.context = getApplicationContext();
		
	}

	public static Context getAppContext() {
		return BasicApplication.context;
	}

	public static AlarmManager getAlarmManager() {
		return (AlarmManager) getAppContext().getSystemService(Context.ALARM_SERVICE);
	}

	public static int getActivitiesRunning() {
		return activitiesRunning;
	}

	private static void setActivitiesRunning(int activitiesRunning) {
		BasicApplication.activitiesRunning = activitiesRunning;
	}
	
	public static void incActivitiesRunning() {
		
		if (getActivitiesRunning() == 0) {
			//	This application is now active
			Log.d(TAG, "This application is now active");
			broadcastStatusChange(APPLICATION_STATUS_ACTIVE);
		}

		setActivitiesRunning(getActivitiesRunning() + 1);
		Log.d(TAG, "incActivitiesRunning : " + getActivitiesRunning());
	}
	
	public static void decActivitiesRunning() {
		if (getActivitiesRunning() > 0) {
			setActivitiesRunning(getActivitiesRunning() - 1);
		}
		Log.d(TAG, "decActivitiesRunning : " + getActivitiesRunning());
			
		if (getActivitiesRunning() == 0) {
			//	This application is no longer active
			Log.d(TAG, "This application is no longer active");
			broadcastStatusChange(APPLICATION_STATUS_NOT_ACTIVE);
		}
	}

	private static void broadcastStatusChange(String data) {
		Log.d(TAG, "broadcastStatusChange : " + data);

		Intent broadcast = new Intent();

		Bundle bundle = new Bundle();
		bundle.putString(APPLICATION_STATUS, data);

		broadcast.putExtras(bundle);
		broadcast.setAction(APPLICATION_STATUS_CHANGED);

		BasicApplication.context.sendBroadcast(broadcast);
	}

}
