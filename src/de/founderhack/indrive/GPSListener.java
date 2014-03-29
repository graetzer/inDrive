package de.founderhack.indrive;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class GPSListener implements LocationListener {

	private int kmh = 0, kmhOld = -1, altitude = 0, direction = 0;
	private float traveledDistance = 0, locationDistance = -1;
	private Location oldLocation;
	private GPSInfoListener listener;
	
	private static Handler handler = new Handler();
	private Geocoder geoCoder;
	private Thread addressSearch = new Thread();
	
	public interface GPSInfoListener{
		public void onSpeedChanged(int kmh);
		public void onAltitudeChanged(int meter);
		public void onDistanceChanged(int distance);
		public void onDirectionChanged(int direction);
		public void onLocationChanged(String[] address);
	}
	
	public GPSListener(Context ctx, GPSInfoListener listener){
		this.listener = listener;
		if(Geocoder.isPresent()){
			geoCoder = new Geocoder(ctx);
		}
	}

	@Override
	public void onLocationChanged(final Location location) {
		kmh = (int) (location.getSpeed() * 3.6f);
		if(kmh != kmhOld){
			listener.onSpeedChanged(kmh);
			kmhOld = kmh;
		}
		
		if(altitude != ((int) location.getAltitude())){
			altitude = (int) location.getAltitude();
			listener.onAltitudeChanged(altitude);
		}
		
		if(direction != (int) (location.getBearing())){
			direction = (int) (location.getBearing());
			listener.onDirectionChanged(direction);
		}
		
		if(oldLocation != null){
			traveledDistance += location.distanceTo(oldLocation);
			locationDistance += location.distanceTo(oldLocation);
		}
	
		oldLocation = location;
		listener.onDistanceChanged((int) traveledDistance);
		
		
		if(!addressSearch.isAlive() && (locationDistance > 200 || locationDistance == -1)){
			addressSearch = new Thread(new Runnable() {
				
				@Override
				public void run() {
					final String[] results = getLocationAddress(location);
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							listener.onLocationChanged(results);
						}
					});
				}
			});
			addressSearch.start();
			locationDistance = 0;
		}
		
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
	
	private String[] getLocationAddress(Location loc){
		String[] result = new String[2];
		if(geoCoder == null) return result;
		
		try {
			List<Address> tmp = geoCoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
			if (tmp.size() != 0) {
				result[0] = tmp.get(0).getAddressLine(0);
				result[1] = tmp.get(0).getLocality();
			}
		} catch (IOException e) {
			e.printStackTrace();
			result = null;
		} catch (NullPointerException e) {
			Log.w("CarAssist", "Geocoding failed.");
			result = null;
		}
		
		return result;
	}
	

}
