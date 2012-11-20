package cam.voell.android.maps;

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
       // Context context = this;
        MonsterReaderDbHelper mDbHelper = new MonsterReaderDbHelper(getBaseContext());
        
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_MONSTER_NAME, "YelpDBMonster");
        values.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_DESCRIPTION, "Near Yelp");
        int lat = (int) (37.7647* 1E6);
        values.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_LATITUDE, String.valueOf(lat));
        int lng = (int)(-122.468* 1E6);
        values.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_LONGITUDE, String.valueOf(lng));
        values.put(MonsterReaderContract.MonsterEntry.COLUMN_NAME_CAUGHT, "0");
        long newRowId;
        newRowId = db.insert(MonsterReaderContract.MonsterEntry.TABLE_NAME, null, values);
        
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
}
