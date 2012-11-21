package cam.voell.android.maps;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
		ArrayList<Monster> mapMonsters = initiateMonstersDB();
		
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
		    0, new GeoUpdateHandler( getBaseContext(), mapController,player,mapMonsters,mapOverlays,userPicOverlay));
		
	}
	
	//This method initiated the monsters on the map.
	private ArrayList<Monster> initiateMonsters(){
	    ArrayList monsters = new ArrayList();
	    monsters.add(new Monster("Outside Lands Monster","You are near your home",(int) (37.7647* 1E6), (int) (-122.468* 1E6)));
	    monsters.add(new Monster("CoitCreature", "You are near Megans",(int) (37.800027 * 1E6), (int) (-122.405537 * 1E6)));
	    monsters.add(new Monster("YelpMonster", "You are near Yelp",(int)(39.362543 * 1E6), (int)(-120.242743 * 1E6)));//(int) (37.785962* 1E6), (int) (-122.402576* 1E6)));  
	    return monsters;
	}
	
	private ArrayList<Monster> initiateMonstersDB(){
		MonsterReaderDbHelper mDbHelper = new MonsterReaderDbHelper(getBaseContext());
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		String[] projection = {
				MonsterReaderContract.MonsterEntry.COLUMN_NAME_MONSTER_NAME,
				MonsterReaderContract.MonsterEntry.COLUMN_NAME_DESCRIPTION,
				MonsterReaderContract.MonsterEntry.COLUMN_NAME_LATITUDE,
				MonsterReaderContract.MonsterEntry.COLUMN_NAME_LONGITUDE
		};
		String sortOrder = MonsterReaderContract.MonsterEntry._ID + " DESC";
		
		Cursor c = db.query(
				MonsterReaderContract.MonsterEntry.TABLE_NAME,
				projection,
				null,
				null,
				null,
				null,
				sortOrder
				);
		c.moveToFirst();
		
		
	    ArrayList<Monster> monsters = new ArrayList<Monster>();
	   // monsters.add(new Monster("YelpMonster", "You are near Yelp",(int)(39.362543 * 1E6), (int)(-120.242743 * 1E6)));
	    String name = c.getString(
				c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_MONSTER_NAME));
	    String description = c.getString(
				c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_DESCRIPTION));
	    int lat = (int)Integer.parseInt(c.getString(
				c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_LATITUDE)));
	    int lng = (int)Integer.parseInt(c.getString(
				c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_LONGITUDE)));
	    monsters.add(new Monster(name, description, lat, lng));
	    
	    c.moveToNext();
	    String name2 = c.getString(
				c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_MONSTER_NAME));
	    String description2 = c.getString(
				c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_DESCRIPTION));
	    int lat2 = (int)Integer.parseInt(c.getString(
				c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_LATITUDE)));
	    int lng2 = (int)Integer.parseInt(c.getString(
				c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_LONGITUDE)));
	    monsters.add(new Monster(name2, description2, lat2, lng2));

	    c.moveToNext();
	    String name3 = c.getString(
				c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_MONSTER_NAME));
	    String description3 = c.getString(
				c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_DESCRIPTION));
	    int lat3 = (int)Integer.parseInt(c.getString(
				c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_LATITUDE)));
	    int lng3 = (int)Integer.parseInt(c.getString(
				c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_LONGITUDE)));
	    monsters.add(new Monster(name3, description3, lat3, lng3));
	   
	    return monsters;
	}
	
}
