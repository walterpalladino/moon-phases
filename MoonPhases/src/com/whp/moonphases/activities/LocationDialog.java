package com.whp.moonphases.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.whp.android.BasicApplication;
import com.whp.android.SharedPreferencesDao;
import com.whp.moonphases.R;

public class LocationDialog extends DialogFragment {

	Activity parentActivity;

	ImageButton imgBtnNorth;
	ImageButton imgBtnSouth;

	View view;
	
	public LocationDialog(Activity parentActivity) {
		// Empty constructor required for DialogFragment
		this.parentActivity = parentActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.choose_location, container);
		getDialog().setTitle(R.string.menu_settings_location);

		setListeners();

		return view;

	}

	private void setListeners() {
		imgBtnNorth = (ImageButton) view.findViewById(R.id.imgBtnNorth);
		imgBtnNorth.setOnClickListener(imgBtnNorthClickListener);
		
		imgBtnSouth = (ImageButton) view.findViewById(R.id.imgBtnSouth);
		imgBtnSouth.setOnClickListener(imgBtnSouthClickListener);

	}
	
	View.OnClickListener imgBtnNorthClickListener = new OnClickListener() {
		public void onClick(View v) {
			
			SharedPreferencesDao dao	= new SharedPreferencesDao(BasicApplication.getAppContext());
			dao.update("moon_phase_hemisphere", "NORTH");
			
			((MainActivity) parentActivity).updateMoonPhase();
			
			LocationDialog.this.dismiss();
		}
	};
	
	View.OnClickListener imgBtnSouthClickListener = new OnClickListener() {
		public void onClick(View v) {
			
			SharedPreferencesDao dao	= new SharedPreferencesDao(BasicApplication.getAppContext());
			dao.update("moon_phase_hemisphere", "SOUTH");

			((MainActivity) parentActivity).updateMoonPhase();

			LocationDialog.this.dismiss();
    		
		}
	};
	
}