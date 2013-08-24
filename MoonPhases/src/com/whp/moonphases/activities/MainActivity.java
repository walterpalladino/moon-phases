package com.whp.moonphases.activities;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


import com.whp.android.BasicApplication;
import com.whp.android.BasicFragmentActivity;
import com.whp.android.ImageManager;
import com.whp.android.Log;
import com.whp.android.SharedPreferencesDao;
import com.whp.android.SimpleGestureFilter;
import com.whp.android.location.BasicLocator.Hemisphere;

import com.whp.moonphases.R;

import com.whp.moonphases.calculator.MoonCalculator;
import com.whp.moonphases.calculator.MoonPhase;

import android.os.Build;
import android.os.Bundle;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.renderscript.ProgramFragmentFixedFunction.Builder.Format;
import android.support.v4.app.DialogFragment;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.DatePicker;

import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends BasicFragmentActivity implements SimpleGestureFilter.SimpleGestureListener {
	
	private SimpleGestureFilter detector; 
	
	
	private static final String TAG = MainActivity.class.getCanonicalName();
	
	private DialogFragment datePickerFragment = null;
	private DialogFragment infoFragment = null;
	private DialogFragment aboutFragment = null;
	private DialogFragment locationFragment = null;

	private Typeface tf	= null;
	
	private static final float MAX_IMAGES_QTY_FLT	= 23.0f;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		if (Build.VERSION.SDK_INT < 11) {
			tf = Typeface.createFromAsset(getAssets(),
		            "fonts/Roboto-Light.ttf");

		}
		
		detector = new SimpleGestureFilter(this,this);
		
		updateMoonPhase();
	}
/*
	@Override
	protected void onPause() {
	    if (datePickerFragment != null && datePickerFragment.isVisible())
	    	datePickerFragment.dismiss();
	    if (infoFragment != null && infoFragment.isVisible())
	    	infoFragment.dismiss();
	    if (aboutFragment != null && aboutFragment.isVisible())
	    	aboutFragment.dismiss();
	    super.onPause();
	}
	*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void updateMoonPhase() {
		
		SharedPreferencesDao dao	= new SharedPreferencesDao(BasicApplication.getAppContext());
		String dateString	= dao.get("moon_phase_date");
		String hemisphereString	= dao.get("moon_phase_hemisphere");
		if (hemisphereString.equals("")) hemisphereString = "NORTH";
		
		Calendar	date	= Calendar.getInstance();
		if ((dateString != null) && (dateString != "")) {
			date.setTimeInMillis(Long.parseLong(dateString));
		}
		Log.d(TAG,String.valueOf( date.getTimeInMillis() ));
		
		double normalizedPhase	= MoonCalculator.getPhase(date);
		int phase = (int) (normalizedPhase * 8.0);
		MoonPhase	moonPhase	= MoonPhase.fromInt(phase);
		
		ImageView	imgMoonPhase	;
		imgMoonPhase	= (ImageView) findViewById(R.id.imgMoonPhase);
		Resources r	= getResources();
		String[] imageArray	= r.getStringArray(R.array.moon_phases_image_list);
		float angle = ("NORTH".equals(hemisphereString))?0.0f:180f;
		try {
			int imageId = (int) (normalizedPhase * MAX_IMAGES_QTY_FLT);
			ImageManager.showImage(imgMoonPhase, imageArray[imageId], angle);
		} catch (IOException e) {
			Log.d(TAG,"run IOException" + e.getMessage());
		}
		
		//TextView moonPhaseDays = (TextView) findViewById(R.id.txtMoonPhaseDays);
		//int moonDays	= (int) (normalizedPhase * MoonCalculator.MOON_PHASE_LENGTH);
		//moonPhaseDays.setText(String.valueOf(moonDays));

		String[] nameArray	= r.getStringArray(R.array.moon_phases_name_list);
		TextView moonPhaseName = (TextView) findViewById(R.id.txtMoonPhaseName);
		
		int moonDays	= (int) (normalizedPhase * MoonCalculator.MOON_PHASE_LENGTH);
		
		moonPhaseName.setText(nameArray[moonPhase.getValue()] + " (" + String.valueOf(moonDays) + ")");
		if (tf != null) moonPhaseName.setTypeface(tf);
		
//		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//		String formattedDate = df.format(date.getTime()); 
		
		DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
		String formattedDate = dateFormat.format(date.getTime());

		TextView moonPhaseDate = (TextView) findViewById(R.id.txtMoonPhaseDate);
		moonPhaseDate.setText(formattedDate);
		if (tf != null) moonPhaseDate.setTypeface(tf);
		
		String[] hemispheres = r.getStringArray(R.array.hemispheres);
		TextView moonPhaseHemisphere = (TextView) findViewById(R.id.txtMoonPhaseHemisphere);
		moonPhaseHemisphere.setText(hemispheres[Hemisphere.valueOf(hemisphereString).getValue()]);
		if (tf != null) moonPhaseHemisphere.setTypeface(tf);
		
	}
	
	
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.hasSubMenu() == false) {
			
			Log.d(TAG,"Item : " + item.getItemId());
			
			switch (item.getItemId()) {
			case R.id.menu_settings_date:
				datePickerFragment = new DatePickerFragment(this);
				Bundle args	= new Bundle();
				datePickerFragment.setArguments(args);
				datePickerFragment.show(getSupportFragmentManager(), "datePicker");
				break;
			case R.id.menu_settings_info:
				infoFragment = new InfoDialog();
				infoFragment.show(getSupportFragmentManager(), "info_dialog");
				break;
			case R.id.menu_settings_about:
				aboutFragment = new AboutDialog();
				aboutFragment.show(getSupportFragmentManager(), "about_dialog");
				break;
			case R.id.menu_settings_location:
				locationFragment = new LocationDialog(this);
				locationFragment.show(getSupportFragmentManager(), "location_dialog");
				break;
			}

		}

		// Consume the selection event.
		return true;
	}

		
	
	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		MainActivity	parentActivity;
		
		public DatePickerFragment() {
		}
		
		public DatePickerFragment(MainActivity parentActivity) {
			this.parentActivity	= parentActivity;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			
			SharedPreferencesDao dao	= new SharedPreferencesDao(BasicApplication.getAppContext());
			String dateString	= dao.get("moon_phase_date");
			Calendar	date	= Calendar.getInstance();
			if ((dateString != null) && (dateString != "")) {
				date.setTimeInMillis(Long.parseLong(dateString));
			}
			
			int year = date.get(Calendar.YEAR);
			int month = date.get(Calendar.MONTH);
			int day = date.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Save the preferences
			SharedPreferencesDao dao	= new SharedPreferencesDao(BasicApplication.getAppContext());
			Calendar	date	= new GregorianCalendar(year, month, day);
			dao.update("moon_phase_date", String.valueOf( date.getTimeInMillis() ));
			this.parentActivity.updateMoonPhase();
		}
	}


	

	@Override
    public boolean dispatchTouchEvent(MotionEvent me){ 

		this.detector.onTouchEvent(me);
		return super.dispatchTouchEvent(me); 

    }


	@Override
	public void onSwipe(int direction) {
		
		switch (direction) {
		case SimpleGestureFilter.SWIPE_RIGHT :
			moveToNextDay();
			break;
		case SimpleGestureFilter.SWIPE_LEFT :
			moveToPrevDay();
			break;
		}
		
	}
	@Override
	public void onDoubleTap() {
		// TODO Auto-generated method stub
		
	}
	

	private void moveToNextDay() {
		SharedPreferencesDao dao	= new SharedPreferencesDao(BasicApplication.getAppContext());
		String dateString	= dao.get("moon_phase_date");
		
		Calendar	date	= Calendar.getInstance();
		if ((dateString != null) && (dateString != "")) {
			date.setTimeInMillis(Long.parseLong(dateString));
		}
		
		date.add(Calendar.DATE, 1);
		
		dao.update("moon_phase_date", String.valueOf( date.getTimeInMillis() ));
		
		updateMoonPhase();
		
	}

	private void moveToPrevDay() {
		
		SharedPreferencesDao dao	= new SharedPreferencesDao(BasicApplication.getAppContext());
		String dateString	= dao.get("moon_phase_date");
		
		Calendar	date	= Calendar.getInstance();
		if ((dateString != null) && (dateString != "")) {
			date.setTimeInMillis(Long.parseLong(dateString));
		}
		
		date.add(Calendar.DATE, -1);
		
		dao.update("moon_phase_date", String.valueOf( date.getTimeInMillis() ));
		
		updateMoonPhase();
		
	}



}
