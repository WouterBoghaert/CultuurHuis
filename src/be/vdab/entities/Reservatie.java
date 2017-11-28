package be.vdab.entities;

import java.io.Serializable;

import be.vdab.util.IntLongUtils;

public class Reservatie implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private Klant klant;
	private Voorstelling voorstelling;
	private int aantalPlaatsen;
	
	public Reservatie(long id, Klant klant, Voorstelling voorstelling, int aantalPlaatsen) {
		if (IntLongUtils.isStrictPositief(id)) {
			this.id = id;
		}
		else {
			throw new IllegalArgumentException();
		}
		if(klant != null) {
			this.klant = klant;
		}
		else {
			throw new IllegalArgumentException();
		}
		if(voorstelling != null) {
			this.voorstelling = voorstelling;
		}
		else {
			throw new IllegalArgumentException();
		}
		if (IntLongUtils.isStrictPositief(aantalPlaatsen)) {
			this.aantalPlaatsen = aantalPlaatsen;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public long getId() {
		return id;
	}

	public Klant getKlant() {
		return klant;
	}

	public Voorstelling getVoorstelling() {
		return voorstelling;
	}

	public int getAantalPlaatsen() {
		return aantalPlaatsen;
	}
}
