package cam.voell.android.maps;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;


public class MonsterMapActivity extends MapActivity {

	private static final int MY_LATITUDE = (int) (37.7650029 * 1E6);
	private static final int MY_LONGITUDE = (int) (-122.4658212 * 1E6);

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.mapview);

		// create a map view
		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setStreetView(true);
		MapController mapController = mapView.getController();
		mapController.setZoom(18); // Zoom 1 is world view

		//Create player and center the map on the player
		Player player = new Player("CameronVoe", "I'm cool", MY_LATITUDE, MY_LONGITUDE,this.getResources().getDrawable(R.drawable.t_m_n_squirtle));
		GeoPoint myLocation = new GeoPoint(MY_LATITUDE,MY_LONGITUDE);
		mapController.animateTo(myLocation);
		
		List mapOverlays = mapView.getOverlays();
		

		//Needs to be called on the update handler method
		Drawable userPic = this.getResources().getDrawable(R.drawable.t_m_n_squirtle);
		MonsterItemizedOverlay userPicOverlay = new MonsterItemizedOverlay(userPic, this);
		OverlayItem overlayItem = new OverlayItem(myLocation, "", "/me waves");
		userPicOverlay.addOverlay(overlayItem);
		mapOverlays.add(userPicOverlay);

		Drawable partyPic = this.getResources().getDrawable(R.drawable.pokeball);
		MonsterItemizedOverlay partyPicOverlay = new MonsterItemizedOverlay(partyPic, this);

		for (Monster monster: getMonstersNear(MY_LATITUDE, MY_LONGITUDE)){
			GeoPoint monster1 = new GeoPoint(monster.getLatitude(), monster.getLongitude());
			OverlayItem partyOverlayItem = new OverlayItem(monster1, "", monster.getDescription());
			partyPicOverlay.addOverlay(partyOverlayItem);
			mapOverlays.add(partyPicOverlay);
		}
		
		//This code updates the location to my location ( ideally have a button for this)
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
		    0, new GeoUpdateHandler(mapController));
	}
	
	private List<Monster> getMonstersNear(int latitude, int longitude){
	    List parties = new ArrayList();
	    parties.add(new Monster("BeLEUVENissen TropicalnOld Marketn20.30 - 21.30 Cookies & Creamn22.00 - 23.30 Vaya Con Dios",(int) (50.878 * 1E6), (int) (4.7 * 1E6)));
	    parties.add(new Monster("Party 2",(int) (50.875 * 1E6), (int) (4.705 * 1E6)));
	    parties.add(new Monster("Party 3",(int) (37.7647 * 1E6), (int) (-122.468 * 1E6)));
	    return parties;
	}
}
