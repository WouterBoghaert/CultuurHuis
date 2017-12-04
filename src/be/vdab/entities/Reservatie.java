package be.vdab.entities;

import java.io.Serializable;

import be.vdab.util.IntLongUtils;

public class Reservatie implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
//	private Klant klant;
//	private Voorstelling voorstelling;
	private long klantId;
	private long voorstellingId;
	private int aantalPlaatsen;
	
	public Reservatie(long id, long klantId, long voorstellingId, int aantalPlaatsen) {
		if (IntLongUtils.isStrictPositief(id)) {
			this.id = id;
		}
		else {
			throw new IllegalArgumentException();
		}
//		if(klant != null) {
//			this.klant = klant;
//		}
//		else {
//			throw new IllegalArgumentException();
//		}
//		if(voorstelling != null) {
//			this.voorstelling = voorstelling;
//		}
//		else {
//			throw new IllegalArgumentException();
//		}
		if (IntLongUtils.isStrictPositief(klantId)) {
			this.klantId = klantId;
		}
		else {
			throw new IllegalArgumentException();
		}
		if (IntLongUtils.isStrictPositief(voorstellingId)) {
			this.voorstellingId = voorstellingId;
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

	/*public Klant getKlant() {
		return klant;
	}

	public Voorstelling getVoorstelling() {
		return voorstelling;
	}*/
	
	public long getKlantId() {
		return klantId;
	}
	
	public long getVoorstellingId() {
		return voorstellingId;
	}

	public int getAantalPlaatsen() {
		return aantalPlaatsen;
	}
	
	public void setId(long id) {
		if (IntLongUtils.isStrictPositief(id)) {
			this.id = id;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservatie other = (Reservatie) obj;
		if (id != other.id)
			return false;
		return true;
	}	
}
