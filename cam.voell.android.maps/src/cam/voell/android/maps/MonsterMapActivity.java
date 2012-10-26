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

		//1) Create a map view
		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setStreetView(true);
		MapController mapController = mapView.getController();
		mapController.setZoom(18); // Zoom 1 is world view

		//2) Create player and center the map on the player
		Player player = new Player("Cameron", "I'm cool", MY_LATITUDE,MY_LONGITUDE,this.getResources().getDrawable(R.drawable.t_m_n_squirtle));
		GeoPoint myLocation = new GeoPoint(MY_LATITUDE, MY_LONGITUDE);
		mapController.animateTo(myLocation);
		
		//3) Create list of monsters
		ArrayList<Monster> mapMonsters = initiateMonsters();
		
		//4) This list will be modified to control what overlays are displayed on the map
		List mapOverlays = mapView.getOverlays();
		
		//5) Add Player on to the map
		//***Player Location will be updated via the GeoUpdateHandler method
		MonsterItemizedOverlay userPicOverlay = new MonsterItemizedOverlay(player.getMyPic(), this);
		OverlayItem overlayItem = new OverlayItem(myLocation, "", "My name is Cameron");
		userPicOverlay.addOverlay(overlayItem);
		mapOverlays.add(0,userPicOverlay);

		//6) Add Monsters to the map
		Drawable genericMonsterPic = this.getResources().getDrawable(R.drawable.pokeball);
		MonsterItemizedOverlay partyPicOverlay = new MonsterItemizedOverlay(genericMonsterPic, this);
		for (Monster monster: mapMonsters){
			GeoPoint monster1 = new GeoPoint(monster.getLatitude(), monster.getLongitude());
			OverlayItem partyOverlayItem = new OverlayItem(monster1, "", monster.getDescription());
			partyPicOverlay.addOverlay(partyOverlayItem);
			mapOverlays.add(partyPicOverlay);
		}
		
		//****************************************************************************
		//This is the most important code in the MonsterMapActivity. This code creates a 
		//Location manager that will listen for any location changes for more than 10 meters
		//and will then call the GeoUpdateHandler onLocationChanged method.
		//Any changes to the player, monster, or mapView that need to occur when a location
		//is changed, need to be called from this onLocationChanged method.
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
		    0, new GeoUpdateHandler(mapController,player,mapMonsters,mapOverlays,userPicOverlay));
		
	}
	
	//This method initiated the monsters on the map.
	private ArrayList<Monster> initiateMonsters(){
	    ArrayList monsters = new ArrayList();
	    monsters.add(new Monster("Outside Lands Monster","You are near your home",(int) (37.7647* 1E6), (int) (-122.468* 1E6)));
	    monsters.add(new Monster("CoitCreature", "You are near Megans",(int) (37.800027 * 1E6), (int) (-122.405537 * 1E6)));
	    monsters.add(new Monster("YelpMonster", "You are near Yelp",(int) (37.785962* 1E6), (int) (-122.402576* 1E6)));  
	    return monsters;
	}
	
}
