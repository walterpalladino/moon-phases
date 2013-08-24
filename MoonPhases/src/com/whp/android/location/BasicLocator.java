package com.whp.android.location;

import com.whp.android.BasicApplication;

import android.location.Location;
import android.location.LocationManager;

public class BasicLocator {

	public enum Hemisphere {
		NORTH(0),
		SOUTH(1);
		
		private final int id;
		
		Hemisphere(int id) { this.id = id;}
		public int getValue() { return id; }
		
	}
	
	public static Hemisphere getHemisphere() {

		Location location = BasicLocator.getLocation();
		if (location != null) {
			return (location.getLongitude() > 0) ? Hemisphere.NORTH : Hemisphere.SOUTH;
		}
		return Hemisphere.SOUTH;
	}

	@SuppressWarnings("static-access")
	public static Location getLocation() {
		LocationManager locationManager = (LocationManager) BasicApplication.getAppContext().getSystemService(BasicApplication.getAppContext().LOCATION_SERVICE);
		return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	}
}
