package com.whp.android;




import android.app.Activity;
import android.os.Bundle;


public class BasicActivity extends Activity {

	private final String TAG = BasicActivity.class.getCanonicalName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onRestart() {
		Log.d(TAG, "BasicActivity onRestart");
		super.onRestart();
	}
	
	@Override
	protected void onResume() {
		Log.d(TAG, "BasicActivity onResume");
		BasicApplication.incActivitiesRunning();
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.d(TAG, "BasicActivity onPause");
		BasicApplication.decActivitiesRunning();
		super.onPause();
	}
	
    @Override
    protected void onDestroy() {
		Log.d(TAG, "BasicActivity onDestroy");
//		BaseApplication.decActivitiesRunning();
        super.onDestroy();
    }
}
