package cam.voell.android.maps;

public class Monster {

		private String name;
		private String description;
		private int latitude;
		private int longitude;

		public Monster(){}
		public Monster(String name, String description, int latitude, int longitude) {
			super();
			this.setName(name);
			this.description = description;
			this.latitude = latitude;
			this.longitude = longitude;
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

	}

