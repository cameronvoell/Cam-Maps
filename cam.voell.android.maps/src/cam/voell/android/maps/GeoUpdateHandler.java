package cam.voell.android.maps;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.OverlayItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class GeoUpdateHandler implements LocationListener {
	
	private MapController mapController;
	private MonsterItemizedOverlay myUserPicOverlay;
	private List myMapOverlays;
	
	private Player player;
	private ArrayList<Monster> monsters;
	
	private int newLatitude;
	private int newLongitude;
	private Context context;

	public GeoUpdateHandler(Context c, MapController mapController, Player p, ArrayList monsters, List mapOverlays, MonsterItemizedOverlay userPicOverlay){
		this.mapController = mapController;
		myUserPicOverlay = userPicOverlay;
		myMapOverlays = mapOverlays;
		context = c;
		player = p;
		this.monsters = monsters;
		
	}

	//This method is called any time a player location changes by more than 10 meters.
	public void onLocationChanged(Location location) {
		newLatitude = (int) (location.getLatitude() * 1E6);
		newLongitude = (int) (location.getLongitude() * 1E6);
		player.setLatitude(newLatitude);
		player.setLongitude(newLongitude);
		Monster closeBy = playerNearMonster();
		float dist = player.distanceTo(closeBy.getLatitude(),closeBy.getLongitude());
		String message;
		if (dist < 30)
		{
			message = "You are currently within range of a " + closeBy.getName() + "!";
			/*
			MonsterReaderDbHelper mDbHelper = new MonsterReaderDbHelper(context);
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
			db.close();*/
		}
		else
		{
			message = "You are " + String.valueOf(dist) + " meters away from "+ closeBy.getName();
		} 
		GeoPoint point = new GeoPoint(newLatitude, newLongitude);
			
		myUserPicOverlay.removeOverlay(0);
		myUserPicOverlay.addOverlay(new OverlayItem(point, "", message));
		myMapOverlays.set(0,myUserPicOverlay);
		mapController.animateTo(point);
		
		
	}
	
	private Monster playerNearMonster()
	{
		boolean foundAMonster = false;
		float minDist = 99999999;
		Monster close = monsters.get(0);
		for (Monster m: monsters)
		{
			float dist = player.distanceTo(m.getLatitude(),m.getLongitude());
			if (dist < minDist)
			{
				minDist = dist;
				close = m;
			}
			return close;
		}
		float sample = player.distanceTo(monsters.get(2).getLatitude(), monsters.get(2).getLongitude());
		return new Monster("Invisible", String.valueOf(sample), 0, 0);
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
		return newLatitude;
	}
	public int getLng()
	{
		return newLongitude;
	}


	

}
