package fr.masterdapm.toulon.sql;

import com.google.android.gms.maps.model.BitmapDescriptor;


public class PointRando {
	private double latitude;
	private double longitude;   
	private int idPoint;
	private String typePoint;
	private int FK_Rando;
	private String textWP;
	private String description;
	private float couleur;
	public PointRando() {
		super();
	}   
	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}   
	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}   
	public int getIdPoint() {
		return this.idPoint;
	}

	public void setIdPoint(int idPoint) {
		this.idPoint = idPoint;
	}   
	public String getTypePoint() {
		return this.typePoint;
	}

	public void setTypePoint(String typePoint) {
		this.typePoint = typePoint;
	}
	public int getFK_Rando() {
		return FK_Rando;
	}
	public void setFK_Rando(int FK_Rando) {
		this.FK_Rando = FK_Rando;
	}
	public String getTextWP() {
		return textWP;
	}
	public void setTextWP(String textWP) {
		this.textWP = textWP;
	}
	@Override
	public String toString() {
		return "PointRando [latitude=" + latitude + ", longitude=" + longitude
				+ ", idPoint=" + idPoint + ", typePoint=" + typePoint
				+ ", FK_Rando=" + FK_Rando + "]";
	}
	public PointRando(double latitude, double longitude,
			String typePoint, int fK_Rando, String textWP,String pDesc, float pCouleur) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.typePoint = typePoint;
		this.FK_Rando = fK_Rando;
		this.textWP = textWP;
		this.description = pDesc;
		this.couleur = pCouleur;
	}
	public PointRando(int id,double latitude, double longitude,
			String typePoint, int fK_Rando, String textWP,String pDesc, float pCouleur) {
		super();
		this.idPoint = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.typePoint = typePoint;
		this.FK_Rando = fK_Rando;
		this.textWP = textWP;
		this.description = pDesc;
		this.couleur = pCouleur;
	}
	public PointRando(int idPoint,double latitude, double longitude,
			String typePoint) {
		super();
		this.idPoint = idPoint;
		this.latitude = latitude;
		this.longitude = longitude;
		this.typePoint = typePoint;
		
	}
	
	public PointRando(double latitude, double longitude,
			String typePoint, String pDesc, float pCouleur) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.typePoint = typePoint;
		this.description = pDesc;
		this.couleur = pCouleur;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getCouleur() {
		return couleur;
	}
	public void setCouleur(float couleur) {
		this.couleur = couleur;
	}
	
}
