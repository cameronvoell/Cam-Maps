package cam.voell.android.maps;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

//*****************************************************************************
//This class represents the object that will be overlayed on to the Monster Map
//One important aspect of this class is that it contains the onTap method for 
//when a user taps on the icon of an object on the map
//*****************************************************************************
public class MonsterItemizedOverlay extends ItemizedOverlay {
	private ArrayList mOverlays = new ArrayList();
	private Context context;

	public MonsterItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		this.context = context;
	}

	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}
	public void addOverlay(int i, OverlayItem overlay){
		mOverlays.add(i,overlay);
		populate();
	}
	public void removeOverlay(int i)
	{
		mOverlays.remove(i);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return (OverlayItem) mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	@Override
	protected boolean onTap(int i) {
		
		OverlayItem overlayItem = (OverlayItem) mOverlays.get(i);
		Toast.makeText(context, overlayItem.getSnippet(), Toast.LENGTH_SHORT).show();
		return true;
	}

	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}

}
