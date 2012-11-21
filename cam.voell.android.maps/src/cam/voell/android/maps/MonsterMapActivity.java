package cam.voell.android.maps;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MonsterMapActivity extends MapActivity {

	private static final int MY_LATITUDE = (int) (37.7650029 * 1E6);
	private static final int MY_LONGITUDE = (int) (-122.4658212 * 1E6);
	private ArrayList<Monster> mapMonsters;
	private Player player;

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
		//mapView.getZoomButtonsController().setVisible(false);
		mapView.setBuiltInZoomControls(false);
		mapView.setStreetView(true);
		MapController mapController = mapView.getController();
		mapController.setZoom(18); // Zoom 1 is world view
		
		Button capture = (Button) findViewById(R.id.button);
		
		

		//2) Create player and center the map on the player
		player = new Player("Cameron", "I'm cool", MY_LATITUDE,MY_LONGITUDE,this.getResources().getDrawable(R.drawable.t_m_n_squirtle));
		GeoPoint myLocation = new GeoPoint(MY_LATITUDE, MY_LONGITUDE);
		mapController.animateTo(myLocation);
		
		//3) Create list of monsters
		mapMonsters = initiateMonstersDB();
		
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
	
	public void captureAttempt(View v)
	{
		Monster closeBy = playerNearMonster();
		int threshold = 30;
		float dist = player.distanceTo(closeBy.getLatitude(),closeBy.getLongitude());
		if (dist < threshold)
		{
			Toast.makeText(getBaseContext(), "You caught " + closeBy.getName()+ "!", Toast.LENGTH_SHORT).show();
			MonsterReaderDbHelper mDbHelper = new MonsterReaderDbHelper(getBaseContext());
			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			
			String[] projection = {MonsterReaderContract.MonsterEntry.COLUMN_NAME_CAUGHT};
			String selection1 = MonsterReaderContract.MonsterEntry.COLUMN_NAME_MONSTER_NAME + " LIKE ?";
			String[] selectionArgs1 = { closeBy.getName() };
			Cursor c = db.query(
					MonsterReaderContract.MonsterEntry.TABLE_NAME,projection,selection1,selectionArgs1,null,null,null);
			// New value for one column
			c.moveToFirst();
			int currentCaught = (int)Integer.valueOf(c.getString(c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_CAUGHT)));
			ContentValues values = new ContentValues();
			values.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_CAUGHT, currentCaught + 1);
			closeBy.setCaught(currentCaught + 1);

			// Which row to update, based on the ID
			String selection = MonsterReaderContract.MonsterEntry.COLUMN_NAME_MONSTER_NAME + " LIKE ?";
			String[] selectionArgs = { closeBy.getName() };

			db.update(
			    MonsterReaderContract.MonsterEntry.TABLE_NAME,
			    values,
			    selection,
			    selectionArgs);
			db.close();
			
		}else {
			int diff = (int) (dist - threshold);
			Toast.makeText(getBaseContext(), "You need to get "+ diff + " meters closer to " + closeBy.getName(), Toast.LENGTH_SHORT).show();
		}
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
	
	private Monster playerNearMonster()
	{
		boolean foundAMonster = false;
		float minDist = 99999999;
		Monster close = mapMonsters.get(0);
		for (Monster m: mapMonsters)
		{
			float dist = player.distanceTo(m.getLatitude(),m.getLongitude());
			if (dist < minDist)
			{
				minDist = dist;
				close = m;
			}
		}
		return close;
	}
	
	public static float distFrom(double d, double e, double f, double g) {
	    double earthRadius = 3958.75;
	    double dLat = Math.toRadians(f-d);
	    double dLng = Math.toRadians(g-e);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(d)) * Math.cos(Math.toRadians(f)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;

	    int meterConversion = 1609;

	    return new Float(dist * meterConversion).floatValue();
}
	
}
