package cam.voell.android.maps;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class GeoUpdateHandler implements LocationListener {

	private MapController mapController;

	public GeoUpdateHandler(MapController mapController){
		this.mapController = mapController;
	}

	@Override
	public void onLocationChanged(Location location) {
		int lat = (int) (location.getLatitude() * 1E6);
		int lng = (int) (location.getLongitude() * 1E6);
		GeoPoint point = new GeoPoint(lat, lng);
		mapController.animateTo(point);
		//display the user pic at the new location and show the parties over there
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


	

}
