package database;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {
	protected SQLiteDatabase db;

	private static final String CREATE_TABLE_AREA = "CREATE TABLE table_area (description TEXT, id INTEGER, name TEXT);";
	private static final String CREATE_TABLE_ROOM = "CREATE TABLE table_room (area_id INTEGER, description TEXT, id INTEGER, name TEXT);";
	private static final String CREATE_TABLE_ICON = "CREATE TABLE table_icon (name TEXT, value TEXT, reference INTEGER);";

	private static final String CREATE_TABLE_FEATURE = "CREATE TABLE table_feature (device_feature_model_id TEXT, id INTEGER, device_id INTEGER, device_usage_id TEXT, address TEXT, device_type_id TEXT, description TEXT, name TEXT, state_key TEXT, parameters TEXT, value_type TEXT);";
	private static final String CREATE_TABLE_FEATURE_ASSOCIATION = "CREATE TABLE table_feature_association (place_id INTEGER, place_type TEXT, device_feature_id INTEGER, id INTEGER, device_feature TEXT );";
	private static final String CREATE_TABLE_FEATURE_STATE = "CREATE TABLE table_feature_state (device_id INTEGER, key TEXT, value TEXT);";
	private static final String CREATE_TABLE_FEATURE_MAP = "CREATE TABLE table_feature_map (id, posx INTEGER, posy INTEGER, map TEXT);";
	private static final String CREATE_TABLE_FEATURE_CUSTOM = "CREATE TABLE table_feature_custom (id,device_id INTEGER,state_key TEXT,customname TEXT, widget TEXT, visible BOOLEAN);";

	private static final String DATABASE_NAME = Environment.getExternalStorageDirectory()+"/domodroid/.conf/domodroid.db";
	private static final int DATABASE_VERSION = 2;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.e("DatabaseHelper","DATABASE_NAME = "+DATABASE_NAME);
		
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		db.execSQL(CREATE_TABLE_AREA);
		db.execSQL(CREATE_TABLE_ROOM);
		db.execSQL(CREATE_TABLE_ICON);
		db.execSQL(CREATE_TABLE_FEATURE);
		db.execSQL(CREATE_TABLE_FEATURE_MAP);
		db.execSQL(CREATE_TABLE_FEATURE_ASSOCIATION);
		db.execSQL(CREATE_TABLE_FEATURE_STATE);
		db.execSQL(CREATE_TABLE_FEATURE_CUSTOM);
	}


	@Override
	protected void finalize() {
		if(db!=null)
			db.close();
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("DatabaseHelper", "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS table_area");
		db.execSQL("DROP TABLE IF EXISTS table_room");
		db.execSQL("DROP TABLE IF EXISTS table_icon");
		db.execSQL("DROP TABLE IF EXISTS table_feature");
		db.execSQL("DROP TABLE IF EXISTS table_feature_association");
		db.execSQL("DROP TABLE IF EXISTS table_feature_state");
		db.execSQL("DROP TABLE IF EXISTS table_feature_map");
		db.execSQL("DROP TABLE IF EXISTS table_feature_custom");
        onCreate(db);
	}
	
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("DatabaseHelper", "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS table_area");
		db.execSQL("DROP TABLE IF EXISTS table_room");
		db.execSQL("DROP TABLE IF EXISTS table_icon");
		db.execSQL("DROP TABLE IF EXISTS table_feature");
		db.execSQL("DROP TABLE IF EXISTS table_feature_association");
		db.execSQL("DROP TABLE IF EXISTS table_feature_state");
		db.execSQL("DROP TABLE IF EXISTS table_feature_map");
		db.execSQL("DROP TABLE IF EXISTS table_feature_custom");
        onCreate(db);
	}

	
}
