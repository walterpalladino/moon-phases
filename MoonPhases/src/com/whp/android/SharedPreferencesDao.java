package com.whp.android;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesDao {

	private static final String APP_SHARED_PREFS = "com.whp.moonphases"; 
    
	private SharedPreferences appSharedPrefs;
    private Editor prefsEditor;
    
    public SharedPreferencesDao (Context context) {
    	this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }
    
    
	
	public void add(String key, String entity) {
		prefsEditor.putString(key, entity);
        prefsEditor.commit();
	}

	
	public String get(String key) {
		return appSharedPrefs.getString(key, "");
	}

	
	public void update(String key, String entity) {
		this.add(key,  entity);
	}

	
	public void delete(String key) {
		prefsEditor.remove(key);
	}

	
}
