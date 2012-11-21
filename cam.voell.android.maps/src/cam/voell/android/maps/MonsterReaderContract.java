package cam.voell.android.maps;

import android.provider.BaseColumns;

public class MonsterReaderContract 
{
	private MonsterReaderContract(){}
	
	public static abstract class MonsterEntry implements BaseColumns
	{
		public static final String TABLE_NAME ="monster";
		public static final String COLUMN_NAME_MONSTER_NAME ="name";
		public static final String COLUMN_NAME_DESCRIPTION ="description";
		public static final String COLUMN_NAME_LATITUDE ="latitude";
		public static final String COLUMN_NAME_LONGITUDE ="longitude";
		public static final String COLUMN_NAME_CAUGHT ="caught";
	}
}
