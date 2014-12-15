package fr.masterdapm.toulon.sql;


import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class RandoBDD {
	public int versionBDD = 1;
	public String nomBDD = "randonnee.db";
	private static final String TABLE_RANDO = "table_rando";
	private static final String COLUMN_ID_RANDO = "id_rando";
	private static final int NUM_COLUMN_ID_RANDO = 0;
	private static final String COLUMN_NOM = "nom_rando";
	private static final int NUM_COLUMN_NOM = 1;
	private static final String COLUMN_DESC = "desc_rando";
	private static final int NUM_COLUMN_DESC = 2;
	private static final String COLUMN_DUREE = "duree_rando";
	private static final int NUM_COLUMN_DUREE = 3;
	private static final String COLUMN_DATE = "date_rando";
	private static final int NUM_COLUMN_DATE = 4;
	private static final String COLUMN_DIFFICULTE = "diff_rando";
	private static final int NUM_COLUMN_DIFFICULTE = 5;
	
	private static final String TABLE_POINT = "table_point";
	private static final String COLUMN_ID_POINT = "id_point";
	private static final int NUM_COLUMN_ID_POINT = 0;
	private static final String COLUMN_LAT = "latitude_point";
	private static final int NUM_COLUMN_LAT = 1;
	private static final String COLUMN_LNG = "longitude_point";
	private static final int NUM_COLUMN_LNG = 2;
	private static final String COLUMN_TYPE = "type_point";
	private static final int NUM_COLUMN_TYPE = 3;
	private static final String COLUMN_FK_RANDO = "fk_rando";
	private static final int NUM_COLUMN_FK_RANDO = 4;
	private static final String COLUMN_TEXT_WP = "text_WP";
	private static final int NUM_COLUMN_TEXT_WP = 5;
	private static final String COLUMN_DESC_WP = "desc_WP";
	private static final int NUM_COLUMN_DESC_WP = 6;
	private static final String COLUMN_COLOR_WP = "color_WP";
	private static final int NUM_COLUMN_COLOR_WP = 7;

	private static SQLiteDatabase bdd;
	private BddSQLite maBDDSQLite;

	public RandoBDD(Context cont) {
		// On créer la BDD et ses tables
		maBDDSQLite = new BddSQLite(cont, nomBDD, null, versionBDD);
	}

	public void open() {
		// on ouvre la BDD en écriture
		bdd = maBDDSQLite.getWritableDatabase();
	}

	public void close() {
		// on ferme l'acces à la BDD
		bdd.close();
	}

	public SQLiteDatabase getBDD() {
		return bdd;
	}
	
	public void insertRando(Randonnee r) {
		// Création d'un ContentValues
		ContentValues valuesRando = new ContentValues();
		ContentValues valuesPoint = new ContentValues();
		// on lui ajoute une valeur associe à une clé (qui est le nom de la
		// colonne dans laquelle on veut mettre la valeur)
		valuesRando.put(COLUMN_ID_RANDO, r.getIdRando());
		valuesRando.put(COLUMN_NOM, r.getNomRando());
		valuesRando.put(COLUMN_DESC, r.getDescRando());
		valuesRando.put(COLUMN_DUREE, r.getDureeRando());
		valuesRando.put(COLUMN_DATE, r.getDateRando());
		valuesRando.put(COLUMN_DIFFICULTE, r.getDiffRando());
		
		// on ins�re l'objet dans la BDD via le ContentValues
		bdd.insert(TABLE_RANDO, null, valuesRando);
		
		List<PointRando> mesPoints = new ArrayList<PointRando>();
				mesPoints = r.getMesPoints();
		for(PointRando pr : mesPoints){
			valuesPoint.put(COLUMN_ID_POINT, generateurId("point"));
			valuesPoint.put(COLUMN_LAT, pr.getLatitude());
			valuesPoint.put(COLUMN_LNG, pr.getLongitude());
			valuesPoint.put(COLUMN_TYPE, pr.getTypePoint());
			Log.i(" ******** TYPE POINT ", pr.getTypePoint());
			//if(pr.getTypePoint().equals("waypoint")){
				valuesPoint.put(COLUMN_TEXT_WP, pr.getTextWP());
				valuesPoint.put(COLUMN_DESC_WP, pr.getDescription());
				valuesPoint.put(COLUMN_COLOR_WP, pr.getCouleur());
			//}
			valuesPoint.put(COLUMN_FK_RANDO, pr.getFK_Rando());
			// on ins�re l'objet dans la BDD via le ContentValues
			bdd.insert(TABLE_POINT, null, valuesPoint);
		}
		
	}
	
	public static int removeRandoWithId(int id) {
		return bdd.delete(TABLE_RANDO, COLUMN_ID_RANDO+" = "+id+" ", null);
	}
	
	public Randonnee getRandoWithNom(String nom) {
		// Récupère dans un Cursor les valeur correspondant à une rando contenu
		// dans la BDD
		Cursor c1 = bdd.query(TABLE_RANDO, new String[] { COLUMN_ID_RANDO,
				COLUMN_NOM, COLUMN_DESC, COLUMN_DUREE, COLUMN_DATE, COLUMN_DIFFICULTE }, COLUMN_NOM + " LIKE \"" + nom
				+ "\"", null, null, null, null, null);
		int fk = cursorToIdRando(c1);
		Cursor c2 = bdd.query(TABLE_POINT, new String[] { COLUMN_ID_POINT,
				COLUMN_LAT, COLUMN_LNG, COLUMN_TYPE, COLUMN_FK_RANDO, COLUMN_TEXT_WP,COLUMN_DESC_WP,COLUMN_COLOR_WP }, COLUMN_FK_RANDO + " LIKE \"" + fk
				+ "\"", null, null, null, null, null);
		return cursorToRando(c1, c2);	
	}
	
	public Randonnee getRandoWithId(int id) {
		// Récupère dans un Cursor les valeur correspondant à une rando contenu
		// dans la BDD
		Cursor c1 = bdd.query(TABLE_RANDO, new String[] { COLUMN_ID_RANDO,
				COLUMN_NOM, COLUMN_DESC, COLUMN_DUREE, COLUMN_DATE, COLUMN_DIFFICULTE }, COLUMN_ID_RANDO + " LIKE \"" + id
				+ "\"", null, null, null, null, null);
		int fk = cursorToIdRando(c1);
		Cursor c2 = bdd.query(TABLE_POINT, new String[] { COLUMN_ID_POINT,
				COLUMN_LAT, COLUMN_LNG, COLUMN_TYPE, COLUMN_FK_RANDO, COLUMN_TEXT_WP, COLUMN_DESC_WP,COLUMN_COLOR_WP }, COLUMN_FK_RANDO + " LIKE \"" + fk
				+ "\"", null, null, null, null, null);
		return cursorToRando(c1, c2);	
	}
	
	private int cursorToIdRando(Cursor c){
		if (c.getCount() == 0){
			return 0;
		}else{
			c.moveToFirst();
			return (int) c.getInt(NUM_COLUMN_ID_RANDO);
		}
	}
	// Cette méthode permet de convertir un cursor en une Randonnee
	private Randonnee cursorToRando(Cursor c1, Cursor c2) {
		// si aucun element n'a été retourné dans la requete, donc pas de randonnee ac ce nom : on renvoie null
			if (c1.getCount() == 0)
				return null;
		
		// On recupere tout les points de la randonnee
		List<PointRando> mesPoints = new ArrayList<PointRando>();
		c2.moveToFirst();
		int nombrePoints = c2.getCount();
		int i;
		PointRando p;
		for(i=0; i<nombrePoints;i++){
			 p = new PointRando(
					 c2.getInt(NUM_COLUMN_ID_POINT),
					 c2.getDouble(NUM_COLUMN_LAT), 
					 c2.getDouble(NUM_COLUMN_LNG), 
					 c2.getString(NUM_COLUMN_TYPE), 
					 c2.getInt(NUM_COLUMN_FK_RANDO), 
					 c2.getString(NUM_COLUMN_TEXT_WP),
			 		 c2.getString(NUM_COLUMN_DESC_WP),
			 		 c2.getFloat(NUM_COLUMN_COLOR_WP));
			mesPoints.add(p);
			c2.moveToNext();
		}
		

		c1.moveToFirst();
		
		Randonnee rando = new Randonnee(c1.getInt(NUM_COLUMN_ID_RANDO),
				c1.getString(NUM_COLUMN_NOM), 
				c1.getString(NUM_COLUMN_DESC), 
				c1.getLong(NUM_COLUMN_DUREE), 
				c1.getString(NUM_COLUMN_DATE),
				c1.getInt(NUM_COLUMN_DIFFICULTE), mesPoints);
		// On ferme le cursor
		c1.close();
		c2.close();

		// On retourne la randonnee 
		return rando;
	}

	@Override
	public String toString() {
		return "RandoBDD [versionBDD=" + versionBDD + ", nomBDD=" + nomBDD
				+ ", bdd=" + bdd + ", maBDDSQLite=" + maBDDSQLite + "]";
	}
	
	public ArrayList<Randonnee> getAllRandos(){
		ArrayList<Randonnee> mesRandos = new ArrayList<Randonnee>();
		//List<String> mesNomsDeRandos = new ArrayList<String>();
		//String[] args = {"*"};
		/*Je récupere tout les noms des randonnées*/
		Cursor c = bdd.rawQuery("select nom_rando from table_rando", null);
		int i, nbResult = c.getCount();
		if(nbResult == 0){
			return null;
		}
		
		c.moveToFirst();
		for(i=0;i<nbResult;i++){
			Randonnee r = getRandoWithNom(c.getString(0));
			mesRandos.add(r);
			c.moveToNext();
		}
		return mesRandos;
	}
	
	public static int generateurId(String type){
		Cursor c;
		if(type == "rando"){
			c = bdd.rawQuery("select * from table_rando", null);
		}else{
			c = bdd.rawQuery("select * from table_point", null);
		}
		int nbResult = c.getCount();
		if(nbResult == 0){
			return 0; //Si y'a aucun element dans la BDD on renvoi id=0
		}
		//Sinon on regarde l'id de la derniere randonnee
		c.moveToLast();
		int idLast = c.getInt(0);
		//On lui rajoute 1 et on le renvoi
		return (idLast+1);
	}
	
	public static List<PointRando> getAllPoints(){
		List<PointRando> lpoint = new ArrayList<PointRando>();
		
		Cursor c = bdd.rawQuery("select * from table_point", null);
		
		if(c.getCount() == 0){
			return null;
		}
		c.moveToFirst();
		for(int i=0; i< c.getCount(); i++){
			PointRando p = new PointRando(
					c.getInt(NUM_COLUMN_ID_POINT),
					c.getDouble(NUM_COLUMN_LAT),
					c.getDouble(NUM_COLUMN_LNG),
					c.getString(NUM_COLUMN_TYPE),
					c.getInt(NUM_COLUMN_FK_RANDO), 
					c.getString(NUM_COLUMN_TEXT_WP),
			 		c.getString(NUM_COLUMN_DESC_WP),
			 		c.getFloat(NUM_COLUMN_COLOR_WP));
			lpoint.add(p);
			c.moveToNext();
		}
		return lpoint;
	}
	
}












