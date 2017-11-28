package be.vdab.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import be.vdab.util.IntLongUtils;
import be.vdab.util.StringUtils;

public class ReserveringLijn {
	private LocalDateTime datum;
	private String titel;
	private String uitvoerders;
	private BigDecimal prijs;
	private int plaatsen;
	private int vrijeplaatsen;
	
	private ReserveringLijn(LocalDateTime datum, String titel, String uitvoerders,
			BigDecimal prijs, int plaatsen, int vrijeplaatsen) {
		if (datum != null) {
			this.datum = datum;
		}
		else {
			throw new IllegalArgumentException();
		}
		if (StringUtils.isNotEmpty(titel)) {
			this.titel = titel;
		}
		else {
			throw new IllegalArgumentException();
		}
		if (StringUtils.isNotEmpty(uitvoerders)) {
			this.uitvoerders = uitvoerders;
		}
		else {
			throw new IllegalArgumentException();
		}
		if (prijs != null && prijs.compareTo(BigDecimal.ZERO) > 0) {
			this.prijs = prijs;
		}
		else {
			throw new IllegalArgumentException();
		}
		if (IntLongUtils.isStrictPositief(plaatsen)) {
			this.plaatsen = plaatsen;
		}
		else {
			throw new IllegalArgumentException();
		}
		if (IntLongUtils.isNietNegatief(vrijeplaatsen)) {
			this.vrijeplaatsen = vrijeplaatsen;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public LocalDateTime getDatum() {
		return datum;
	}

	public String getTitel() {
		return titel;
	}

	public String getUitvoerders() {
		return uitvoerders;
	}

	public BigDecimal getPrijs() {
		return prijs;
	}

	public int getPlaatsen() {
		return plaatsen;
	}

	public int getVrijeplaatsen() {
		return vrijeplaatsen;
	}
	
	public static class ReserveringLijnBuilder {
		private LocalDateTime datum;
		private String titel;
		private String uitvoerders;
		private BigDecimal prijs;
		private int plaatsen;
		private int vrijeplaatsen;
		
		public ReserveringLijnBuilder metDatum (LocalDateTime datum) {
			this.datum = datum;
			return this;
		}
		
		public ReserveringLijnBuilder metTitel (String titel) {
			this.titel = titel;
			return this;
		}
		
		public ReserveringLijnBuilder metUitvoerders (String uitvoerders) {
			this.uitvoerders = uitvoerders;
			return this;
		}
		
		public ReserveringLijnBuilder metPrijs (BigDecimal prijs) {
			this.prijs = prijs;
			return this;
		}
		
		public ReserveringLijnBuilder metPlaatsen (int plaatsen) {
			this.plaatsen = plaatsen;
			return this;
		}
		
		public ReserveringLijnBuilder metVrijeplaatsen (int vrijeplaatsen) {
			this.vrijeplaatsen= vrijeplaatsen;
			return this;
		}
		
		// nog maak method schrijven
	}
}
