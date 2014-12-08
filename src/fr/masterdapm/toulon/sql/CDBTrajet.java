package fr.masterdapm.toulon.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
public class CDBTrajet {
		//public static final String TABLE_TRAJET         = "trajet";
		public static final String TABLE_WAYPOINT       = "waypoint";
		
		public static final String ID_COLUMN_NAME    = "_id";
		public static final String LAT  			 = "lat";
		public static final String LONG    			 = "long";
		
		
		//public static final String ID_TRAJET       = "_id";		
		
		private DatabaseHelper mDBHelper;
		@SuppressWarnings("unused")
		private Context        mContext;
		private SQLiteDatabase mDb;
		
		
		public CDBTrajet(Context pContext){
			mContext = pContext;
			mDBHelper = new DatabaseHelper(pContext);
		}	
		
		public class DatabaseHelper extends SQLiteOpenHelper{
			private Context mContext;
			
			
			public DatabaseHelper(Context pContext) {
				super(pContext, TABLE_WAYPOINT, null/*factory*/, 1/*version*/);
				mContext = pContext;
			}

			@Override
			public void onCreate(SQLiteDatabase pDb) {
				pDb.execSQL("create table "    + TABLE_WAYPOINT + "("
						+ ID_COLUMN_NAME       + " integer primary key autoincrement, "
					    + LAT    + " text not null, " 
						+ LONG  + " text not null); ");	
				
			}

			@Override
			public void onUpgrade(SQLiteDatabase pDb, int pOldVersion, int pNewVersion) {
				Toast.makeText(mContext, "DB version update from " + pOldVersion + " to " + pNewVersion, Toast.LENGTH_SHORT).show();
				pDb.execSQL("DROP TABLE IF EXISTS " + TABLE_WAYPOINT);
				onCreate(pDb);
			}
		}
		
		public CDBTrajet open(){
			mDb = mDBHelper.getWritableDatabase();
			return this;
		}
		
		public void close(){
			mDb.close();
		}
		
		public void Truncate(){
			mDb.execSQL("delete from " + TABLE_WAYPOINT);
		}
		
		public long insererUnProduit( String pLat, String pLong){
			ContentValues lValues = new ContentValues();
			lValues.put(LAT,    pLat);
			lValues.put(LONG,     pLong);
			return mDb.insert(TABLE_WAYPOINT, null/*nullColumnHack*/, lValues);
		}
		
		
		public boolean supprimerProduit(long pId){
			return mDb.delete(TABLE_WAYPOINT, ID_COLUMN_NAME + pId, null/*whereArgs*/) > 0;
		}
		
		public Cursor recupererLaListeDesProduits(){
			return mDb.query(
					TABLE_WAYPOINT,
				new String[] {
					LAT,
					LONG
				},
				null/*selection*/,
				null/*selectionArgs*/,
				null/*groupBy*/,
				null/*having*/,
				null/*orderBy*/);
		}
		
	}
