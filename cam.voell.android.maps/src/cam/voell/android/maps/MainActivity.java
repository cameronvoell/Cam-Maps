package cam.voell.android.maps;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
