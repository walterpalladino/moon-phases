package com.whp.android;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class BasicFragmentActivity extends FragmentActivity {

	private final String TAG = BasicFragmentActivity.class.getCanonicalName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onRestart() {
		Log.d(TAG, "BasicFragmentActivity onRestart");
		super.onRestart();
	}
	
	@Override
	protected void onResume() {
		Log.d(TAG, "BasicFragmentActivity onResume");
		BasicApplication.incActivitiesRunning();
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.d(TAG, "BasicFragmentActivity onPause");
		BasicApplication.decActivitiesRunning();
		super.onPause();
	}
	
    @Override
    protected void onDestroy() {
		Log.d(TAG, "BasicFragmentActivity onDestroy");
//		BaseApplication.decActivitiesRunning();
        super.onDestroy();
    }
}
