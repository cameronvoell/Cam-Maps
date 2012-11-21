package cam.voell.android.maps;

import java.util.ArrayList;

import android.app.TabActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.widget.TabHost;

public class MainActivity extends TabActivity {
			
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Database stuff
        MonsterReaderDbHelper mDbHelper = new MonsterReaderDbHelper(getBaseContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        
        ArrayList<ContentValues> monstersToAdd = getValuesToInitialize();
        for (ContentValues m: monstersToAdd)
        {
        	
        	db.insert(MonsterReaderContract.MonsterEntry.TABLE_NAME, null, m);
        }
        
        
        TabHost tabHost = getTabHost();

        Intent mapIntent = new Intent().setClass(this, MonsterMapActivity.class);
        TabHost.TabSpec mapTabSpec = tabHost.newTabSpec("map");
        mapTabSpec.setIndicator("Monster Map");
        mapTabSpec.setContent(mapIntent);
        tabHost.addTab(mapTabSpec);
        
        

        // Do the same for the other tabs
        Intent monsterListIntent = new Intent().setClass(this, MonsterListActivity.class);
        TabHost.TabSpec monsterListSpec = tabHost.newTabSpec("monsterList").setIndicator("Monster List").setContent(monsterListIntent);
        tabHost.addTab(monsterListSpec);

        tabHost.setCurrentTab(2);
    }
	
	private ArrayList<ContentValues> getValuesToInitialize()
	{
		ArrayList<ContentValues> values = new ArrayList<ContentValues>();
		//Monster 1
		ContentValues monster1 = new ContentValues();
		monster1.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_MONSTER_NAME, "Werewolf");
        monster1.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_DESCRIPTION, "Many Dingleberries");
        int lat = (int)(39.362543 * 1E6); // (37.7647* 1E6);39.362543,-120.242743
        monster1.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_LATITUDE, String.valueOf(lat));
        int lng = (int)(-120.242743 * 1E6); //(-122.468* 1E6);
        monster1.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_LONGITUDE, String.valueOf(lng));
        monster1.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_CAUGHT, "0");
        values.add(monster1);
        
		//Monster 2
		ContentValues monster2 = new ContentValues();
		monster2.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_MONSTER_NAME, "CoddleFish");
        monster2.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_DESCRIPTION, "Stinks on the way out");
        int lat2 = (int)(39.36377 * 1E6); // 39.36377,-120.236778
        monster2.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_LATITUDE, String.valueOf(lat2));
        int lng2 = (int)(-120.236778 * 1E6); //(-122.468* 1E6);
        monster2.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_LONGITUDE, String.valueOf(lng2));
        monster2.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_CAUGHT, "0");
        values.add(monster2);
        
		//Monster 3
		ContentValues monster3 = new ContentValues();
		monster3.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_MONSTER_NAME, "Moose Monster");
        monster3.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_DESCRIPTION, "Tasty Drool");
        int lat3 = (int)(39.363671 * 1E6); // 39.363671,-120.239739
        monster3.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_LATITUDE, String.valueOf(lat3));
        int lng3 = (int)(-120.239739 * 1E6); //(-122.468* 1E6);
        monster3.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_LONGITUDE, String.valueOf(lng3));
        monster3.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_CAUGHT, "0");
        values.add(monster3);
        
		
		return values;
	}
}
