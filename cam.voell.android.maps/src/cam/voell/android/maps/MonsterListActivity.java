package cam.voell.android.maps;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MonsterListActivity extends Activity 
{
	protected void onCreate(Bundle bundle) 
	{
		super.onCreate(bundle);
		setContentView(R.layout.listview);
		
		ArrayList<String> s = new ArrayList<String>();
        s.add("Cameron");
        s.add("Voell");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, s);
        ListView listview = (ListView)findViewById(R.id.myList);
        listview.setAdapter(adapter);
	}
}
