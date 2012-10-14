package cam.voell.android.maps;

import android.graphics.drawable.Drawable;

//This Class represents the player of the game. There should only be one instantiation of this class.
//Each  player will have a 1)Name, 2) Location, and 3) Picture
public class Player {
	private String name;
	private String description;
	private int latitude;
	private int longitude;
	Drawable myPic;

	public Player(){}
	public Player(String name, String description, int latitude, int longitude, Drawable d) {
		super();
		this.setName(name);
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.myPic = d;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
}
