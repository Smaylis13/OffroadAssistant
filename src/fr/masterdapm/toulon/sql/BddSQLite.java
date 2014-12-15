package fr.masterdapm.toulon.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class BddSQLite extends SQLiteOpenHelper {
	public int versionBDD = 1;
	
	private static final String TABLE_RANDO = "table_rando";
	private static final String COLUMN_ID_RANDO = "id_rando";
	private static final String COLUMN_NOM = "nom_rando";
	private static final String COLUMN_DESC = "desc_rando";
	private static final String COLUMN_DUREE = "duree_rando";
	private static final String COLUMN_DATE = "date_rando";
	private static final String COLUMN_DIFFICULTE = "diff_rando";

	private static final String TABLE_POINT = "table_point";
	private static final String COLUMN_ID_POINT = "id_point";
	private static final String COLUMN_LAT = "latitude_point";
	private static final String COLUMN_LNG = "longitude_point";
	private static final String COLUMN_TYPE = "type_point";
	private static final String COLUMN_FK_RANDO = "fk_rando";
	private static final String COLUMN_TEXT_WP = "text_wp";
	private static final String COLUMN_DESC_WP = "desc_WP";
	private static final String COLUMN_COLOR_WP = "color_WP";

	private static final String CREATION_BDD1 = "CREATE TABLE " + TABLE_RANDO
			+ " (" + COLUMN_ID_RANDO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_DATE + " DATE, " + COLUMN_DESC + " TEXT NOT NULL, "
			+ COLUMN_DIFFICULTE + " INTEGER NOT NULL, " + COLUMN_DUREE
			+ " INTEGER, " + COLUMN_NOM + " TEXT NOT NULL );";
	private static final String CREATION_BDD2 = "CREATE TABLE " + TABLE_POINT
			+ " (" + COLUMN_ID_POINT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_LAT     +" REAL, " + COLUMN_LNG + " REAL, "
			+ COLUMN_TYPE    +" TEXT NOT NULL, "
			+ COLUMN_FK_RANDO+" INTEGER NOT NULL, "
			+ COLUMN_TEXT_WP +" TEXT NOT NULL, "
			+ COLUMN_DESC_WP +" TEXT, "
			+ COLUMN_COLOR_WP+" FLOAT, FOREIGN KEY (" + COLUMN_FK_RANDO
			+ ") REFERENCES "+ TABLE_RANDO + " ( " + COLUMN_ID_RANDO +" ));";

	// private static final String CREATION_FK =
	// "ALTER TABLE "+TABLE_POINT+" ADD CONSTRAINT FK_Rando FOREIGN KEY ("+COLUMN_ID_RANDO+") REFERENCES "+TABLE_RANDO+" ("+COLUMN_ID_RANDO+");";

	public BddSQLite(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATION_BDD1);
		db.execSQL(CREATION_BDD2);
		// db.execSQL(CREATION_FK);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/*Lorsque l'on change le num�ro de version de la base on supprime les tables puis on la recr�e*/
		    if (newVersion > versionBDD) {
		        db.execSQL("DROP TABLE " +TABLE_RANDO+ ";");
		        db.execSQL("DROP TABLE " +TABLE_POINT+ ";");
		        onCreate(db);
		    }

	}

}
