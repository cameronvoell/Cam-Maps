package cam.voell.android.maps;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.OverlayItem;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class GeoUpdateHandler implements LocationListener {
	
	private int latitude;
	private int longitude;
	private MapController mapController;
	private Player myPlayer;
	private List myMapOverlays;
	private MonsterItemizedOverlay myUserPicOverlay;

	public GeoUpdateHandler(MapController mapController, Player p, List mapOverlays, MonsterItemizedOverlay userPicOverlay){
		this.mapController = mapController;
		myPlayer = p;
		myUserPicOverlay = userPicOverlay;
		myMapOverlays = mapOverlays;
	}

	@Override
	public void onLocationChanged(Location location) {
		latitude = (int) (location.getLatitude() * 1E6);
		myPlayer.setLatitude(latitude);
		longitude = (int) (location.getLongitude() * 1E6);
		myPlayer.setLongitude(longitude);
		GeoPoint point = new GeoPoint(latitude, longitude);
		
		myUserPicOverlay.removeOverlay(0);
		myUserPicOverlay.addOverlay(new OverlayItem(point, "", "I'm at" + latitude + " , " + longitude));
		myMapOverlays.set(0,myUserPicOverlay);
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
	public int getLat()
	{
		return latitude;
	}
	public int getLng()
	{
		return longitude;
	}


	

}
