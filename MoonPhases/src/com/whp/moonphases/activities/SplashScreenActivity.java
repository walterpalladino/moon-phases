package com.whp.moonphases.activities;




import java.io.IOException;

import com.whp.android.BasicActivity;
import com.whp.android.ImageManager;
import com.whp.android.Log;
import com.whp.moonphases.R;


import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;

import android.widget.ImageView;


public class SplashScreenActivity extends BasicActivity {

	private static final String TAG = SplashScreenActivity.class
			.getCanonicalName();

	protected Runnable postDelayedAction;

	protected long splashScreenDelay = 2000L;
	
	protected String nextActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		Log.d(TAG, "onCreate");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		try {
			ApplicationInfo ai = getPackageManager().getApplicationInfo(
					getPackageName(), PackageManager.GET_META_DATA);
			Bundle bundle = ai.metaData;
			Log.d(TAG, "splashScreenDelay string : " + (String)bundle.get("splash_screen_delay"));
			splashScreenDelay = Long.parseLong((String)bundle.get("splash_screen_delay"));
			Log.d(TAG, "splashScreenDelay : " + splashScreenDelay);
			
			nextActivity	= bundle.getString("splash_screen_next_activity");
			Log.d(TAG, "nextActivity : " + nextActivity);
		
		} catch (NameNotFoundException e) {
			Log.e(TAG,
					"Failed to load meta-data, NameNotFound: " + e.getMessage());
		} catch (NullPointerException e) {
			Log.e(TAG,
					"Failed to load meta-data, NullPointer: " + e.getMessage());
		} catch (NumberFormatException e) {
			Log.e(TAG,
					"Failed to load meta-data, NumberFormatException: "
							+ e.getMessage());

		}



		doStuff();


	}

	@SuppressWarnings("rawtypes")
	protected void switchActivity() {
	
		Class classz = null;
		try {
			classz = Class.forName(nextActivity);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// start the home screen
		Intent intent = new Intent(SplashScreenActivity.this,
				//LoginActivity.class);
				classz);
		SplashScreenActivity.this.startActivity(intent);
	}
	

	
	private ImageView	imgSplashScreen	;
	private String[] imageArray;
	private Handler handler ;
	
	private void doStuff() {
		Log.d(TAG, "doStuff");

		imgSplashScreen	= (ImageView) findViewById(R.id.imgSplashScreen);
		
		Resources r	= getResources();
		imageArray = r.getStringArray(R.array.splash_image_list);

		if (imageArray.length > 0) {
			try {
				ImageManager.showImage(imgSplashScreen, imageArray[0]);
			} catch (IOException e) {
				Log.d(TAG,"run IOException" + e.getMessage());
			}
		}
		
		handler = new Handler();

		Runnable runnable = new Runnable() {
			int	i = 1;
			@Override
			public void run() {
				
				if (imageArray.length > 1) {
					try {
						ImageManager.showImage(imgSplashScreen, imageArray[i]);
					} catch (IOException e) {
						Log.d(TAG,"run IOException" + e.getMessage());
					}
				}
                i++;
                if(i > imageArray.length-1)
                {
    				// make sure we close the splash screen so the user won't come
    				// back when it presses back key
    				finish();
    				switchActivity();
                } else {
                	handler.postDelayed(this, splashScreenDelay); 
                }
                
			}
        };

		// run a thread after 2 seconds to start the home screen
		handler.postDelayed(runnable, splashScreenDelay); 
	}
	
}
