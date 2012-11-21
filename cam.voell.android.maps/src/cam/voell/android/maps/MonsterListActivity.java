package cam.voell.android.maps;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MonsterListActivity extends Activity 
{
	private SimpleCursorAdapter ca;
	private Cursor c;
	public void onResume()
	{
		c.requery();
		super.onResume();
	}
	protected void onCreate(Bundle bundle) 
	{
		//*****************************
		//This is the Monster List View
		//*****************************
		super.onCreate(bundle);
		setContentView(R.layout.listview);
	
		//**********************************************************************************
		//These values will be used for an ArrayAdapter technique of populating the ListView
		//**********************************************************************************
		ArrayList monsters = initiateMonstersDB();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, monsters);
        
        //***********************************************************************************
        //These values will be used for a CursorAdapter technique for populating the ListView
        //***********************************************************************************
        MonsterReaderDbHelper mDbHelper = new MonsterReaderDbHelper(getBaseContext());
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		String[] projection = {
				MonsterReaderContract.MonsterEntry._ID,
				MonsterReaderContract.MonsterEntry.COLUMN_NAME_MONSTER_NAME,
				MonsterReaderContract.MonsterEntry.COLUMN_NAME_DESCRIPTION,
				MonsterReaderContract.MonsterEntry.COLUMN_NAME_LATITUDE,
				MonsterReaderContract.MonsterEntry.COLUMN_NAME_CAUGHT
		};
		String sortOrder = MonsterReaderContract.MonsterEntry.COLUMN_NAME_CAUGHT + " DESC";
		
		c = db.query(
				MonsterReaderContract.MonsterEntry.TABLE_NAME,
				projection,
				null,
				null,
				null,
				null,
				sortOrder
				);
		c.moveToFirst();
		String[] fromColumns = {MonsterReaderContract.MonsterEntry.COLUMN_NAME_MONSTER_NAME, MonsterReaderContract.MonsterEntry.COLUMN_NAME_CAUGHT};
		int[] toViews = {R.id.monster_name,R.id.monster_found};
        ca = new SimpleCursorAdapter(getBaseContext(), R.layout.monster_entry, c, fromColumns, toViews,1);
      
        //******************************
        //Add the adaptor to my ListView
        //******************************
        ListView listview = (ListView)findViewById(R.id.myList);
        listview.setAdapter(ca);
	}
	
	private ArrayList<Monster> initiateMonsters(){
	    ArrayList monsters = new ArrayList();
	    monsters.add(new Monster("Outside Lands Monster","Near your home",(int) (37.7647* 1E6), (int) (-122.468* 1E6)));
	    monsters.add(new Monster("CoitCreature", "Near Megans",(int) (37.800027 * 1E6), (int) (-122.405537 * 1E6)));
	    monsters.add(new Monster("YelpMonster", "Near Yelp",(int) (37.785962* 1E6), (int) (-122.402576* 1E6)));  
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
	    monsters.add(new Monster(
	    						c.getString(
	    								c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_MONSTER_NAME)),
	    						c.getString(
	    								c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_DESCRIPTION)),
	    						Integer.parseInt(c.getString(
	    	    						c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_LATITUDE))),
	    	    				Integer.parseInt(c.getString(
	    	    						c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_LONGITUDE)))
	    		)); 
	    c.moveToNext();
	    monsters.add(new Monster(
				c.getString(
						c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_MONSTER_NAME)),
				c.getString(
						c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_DESCRIPTION)),
				Integer.parseInt(c.getString(
						c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_LATITUDE))),
				Integer.parseInt(c.getString(
						c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_LONGITUDE)))
	    		)); 
	    c.moveToNext();
	    monsters.add(new Monster(
				c.getString(
						c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_MONSTER_NAME)),
				c.getString(
						c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_DESCRIPTION)),
				Integer.parseInt(c.getString(
						c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_LATITUDE))),
				Integer.parseInt(c.getString(
						c.getColumnIndex(MonsterReaderContract.MonsterEntry.COLUMN_NAME_LONGITUDE)))
	    		)); 
	    return monsters;
	}
}
