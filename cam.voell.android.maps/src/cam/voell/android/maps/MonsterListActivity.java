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
		
		ArrayList monsters = initiateMonsters();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, monsters);
        ListView listview = (ListView)findViewById(R.id.myList);
        listview.setAdapter(adapter);
	}
	
	private ArrayList<Monster> initiateMonsters(){
	    ArrayList monsters = new ArrayList();
	    monsters.add(new Monster("Outside Lands Monster","Near your home",(int) (37.7647* 1E6), (int) (-122.468* 1E6)));
	    monsters.add(new Monster("CoitCreature", "Near Megans",(int) (37.800027 * 1E6), (int) (-122.405537 * 1E6)));
	    monsters.add(new Monster("YelpMonster", "Near Yelp",(int) (37.785962* 1E6), (int) (-122.402576* 1E6)));  
	    return monsters;
	}
}
