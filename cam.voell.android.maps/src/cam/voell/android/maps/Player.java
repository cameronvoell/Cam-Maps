package cam.voell.android.maps;

import com.google.android.maps.GeoPoint;

import android.graphics.drawable.Drawable;

//This Class represents the player of the game. There should only be one instantiation of this class.
//Each  player will have a 1)Name, 2) Location, and 3) Picture
public class Player {
	private String name;
	private String description;
	Drawable myPic;
	private int latitude;
	private int longitude;

	public Player(){}
	public Player(String name, String description, int latitude, int longitude, Drawable d) {
		super();
		this.setName(name);
		this.description = description;
		this.myPic = d;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public float distanceTo(int lat, int lng)
	{
		return distFrom(latitude / 1E6,longitude / 1E6,lat/1E6,lng/1E6);
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

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Drawable getPic(){
		return myPic;
	}
	public void setPic(Drawable d)
	{
		myPic = d;
	}
	public int getLatitude() {
		return latitude;
	}
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	public int getLongitude() {
		return longitude;
	}
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	public Drawable getMyPic() {
		return myPic;
	}
	public void setMyPic(Drawable myPic) {
		this.myPic = myPic;
	}
}
