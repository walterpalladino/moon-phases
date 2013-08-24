package com.whp.moonphases.activities;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whp.moonphases.R;

public class InfoDialog extends DialogFragment {

	public InfoDialog() {
		// Empty constructor required for DialogFragment
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.info, container);
		getDialog().setTitle(R.string.menu_settings_info);
		return view;

	}
}