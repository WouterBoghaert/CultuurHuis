package be.vdab.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import be.vdab.util.IntLongUtils;
import be.vdab.util.StringUtils;

public class Voorstelling {
	private long id;
	private String titel;
	private String uitvoerders;
	private LocalDateTime datum;
	private Genre genre;
	private BigDecimal prijs;
	private int aantalVrijePlaatsen;
	
	private Voorstelling(long id, String titel, String uitvoerders,
			LocalDateTime datum, Genre genre, BigDecimal prijs, int aantalVrijePlaatsen) {
		if(IntLongUtils.isStrictPositief(id)) {
			this.id = id;
		}
		else {
			throw new IllegalArgumentException();
		}
		if(StringUtils.isNotEmpty(titel)) {
			this.titel = titel;
		}
		else {
			throw new IllegalArgumentException();
		}
		if(StringUtils.isNotEmpty(uitvoerders)) {
			this.uitvoerders = uitvoerders;
		}
		else {
			throw new IllegalArgumentException();
		}
		if (datum != null) {
			this.datum = datum;
		}
		else {
			throw new IllegalArgumentException();
		}
		if (genre != null) {
			this.genre = genre;
		}
		else {
			throw new IllegalArgumentException();
		}
		if(prijs.compareTo(BigDecimal.ZERO) > 0) {
			this.prijs = prijs;
		}
		else {
			throw new IllegalArgumentException();
		}
		if(IntLongUtils.isNietNegatief(aantalVrijePlaatsen)) {
			this.aantalVrijePlaatsen = aantalVrijePlaatsen;
		}
		else {
			throw new IllegalArgumentException();
		}		
	}

	public long getId() {
		return id;
	}

	public String getTitel() {
		return titel;
	}

	public String getUitvoerders() {
		return uitvoerders;
	}

	public LocalDateTime getDatum() {
		return datum;
	}

	public Genre getGenre() {
		return genre;
	}

	public BigDecimal getPrijs() {
		return prijs;
	}

	public int getAantalVrijePlaatsen() {
		return aantalVrijePlaatsen;
	}
	
	public static class VoorstellingBuilder {
		private long id;
		private String titel;
		private String uitvoerders;
		private LocalDateTime datum;
		private Genre genre;
		private BigDecimal prijs;
		private int aantalVrijePlaatsen;
		
		public VoorstellingBuilder metId(long id) {
			this.id = id;
			return this;
		}
		
		public VoorstellingBuilder metTitel(String titel) {
			this.titel = titel;
			return this;
		}
		
		public VoorstellingBuilder metUitvoerders(String uitvoerders) {
			this.uitvoerders = uitvoerders;
			return this;
		}
		
		public VoorstellingBuilder metDatum(LocalDateTime datum) {
			this.datum = datum;
			return this;
		}
		
		public VoorstellingBuilder metGenre (Genre genre) {
			this.genre = genre;
			return this;
		}
		
		public VoorstellingBuilder metPrijs (BigDecimal prijs) {
			this.prijs = prijs;
			return this;
		}
		
		public VoorstellingBuilder metAantalVrijePlaatsen (int aantalVrijePlaatsen) {
			this.aantalVrijePlaatsen = aantalVrijePlaatsen;
			return this;
		}
		
		public Voorstelling maakVoorstelling() {
			return new Voorstelling (id, titel, uitvoerders, datum, genre,
					prijs, aantalVrijePlaatsen);
		}
	}
}
