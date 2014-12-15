package fr.masterdapm.toulon.sql;

import java.util.List;

public class Randonnee {
	private int idRando;
	private String nomRando;
	private String descRando;
	private long dureeRando;
	private String dateRando;
	private int diffRando;
	private List<PointRando> mesPoints;

	public Randonnee() {
		super();
	}   
	public int getIdRando() {
		return this.idRando;
	}

	public void setIdRando(int idRando) {
		this.idRando = idRando;
	}   
	public String getNomRando() {
		return this.nomRando;
	}

	public void setNomRando(String nomRando) {
		this.nomRando = nomRando;
	}   
	public String getDescRando() {
		return this.descRando;
	}

	public void setDescRando(String descRando) {
		this.descRando = descRando;
	}   
	public long getDureeRando() {
		return this.dureeRando;
	}

	public void setDureeRando(long dureeRando) {
		this.dureeRando = dureeRando;
	}   
	public String getDateRando() {
		return this.dateRando;
	}

	public void setDateRando(String dateRando) {
		this.dateRando = dateRando;
	}   
	public int getDiffRando() {
		return this.diffRando;
	}

	public void setDiffRando(int diffRando) {
		this.diffRando = diffRando;
	}
	public List<PointRando> getMesPoints() {
		return mesPoints;
	}
	public void setMesPoints(List<PointRando> mesPoints) {
		this.mesPoints = mesPoints;
	}
	@Override
	public String toString() {
		return "Randonnee [idRando=" + idRando + ", nomRando=" + nomRando
				+ ", descRando=" + descRando + ", dureeRando=" + dureeRando
				+ ", dateRando=" + dateRando + ", diffRando=" + diffRando
				+ ", mesPoints=" + mesPoints + "]";
	}
	public Randonnee(int idRando, String nomRando, String descRando,
			long dureeRando, String dateRando, int diffRando,
			List<PointRando> mesPoints) {
		super();
		this.idRando = idRando;
		this.nomRando = nomRando;
		this.descRando = descRando;
		this.dureeRando = dureeRando;
		this.dateRando = dateRando;
		this.diffRando = diffRando;
		this.mesPoints = mesPoints;
	}
	
}
