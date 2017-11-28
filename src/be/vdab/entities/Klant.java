package be.vdab.entities;

import be.vdab.util.IntLongUtils;
import be.vdab.util.StringUtils;

public class Klant {
	private long id;
	private String voornaam;
	private String familienaam;
	private String straat;
	private String huisnr;
	private String postcode;
	private String gemeente;
	private String gebruikersnaam;
	private String paswoord;
	
	private Klant(long id, String voornaam, String familienaam, String straat,
			String huisnr, String postcode, String gemeente, String gebruikersnaam,
			String paswoord) {
		if (IntLongUtils.isStrictPositief(id)) {
			this.id = id;
		}
		if (StringUtils.isNotEmpty(voornaam)) {
			this.voornaam = voornaam;
		}
		if (StringUtils.isNotEmpty(familienaam)) {
			this.familienaam = familienaam;
		}
		if (StringUtils.isNotEmpty(straat)) {
			this.straat = straat;
		}
		if (StringUtils.isNotEmpty(huisnr)) {
			this.huisnr = huisnr;
		}
		if (StringUtils.isNotEmpty(postcode)) {
			this.postcode = postcode;
		}
		if (StringUtils.isNotEmpty(gemeente)) {
			this.gemeente = gemeente;
		}
		if (StringUtils.isNotEmpty(gebruikersnaam)) {
			this.gebruikersnaam = gebruikersnaam;
		}
		if (StringUtils.isNotEmpty(paswoord)) {
			this.paswoord = paswoord;
		}
	}

	public long getId() {
		return id;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public String getFamilienaam() {
		return familienaam;
	}

	public String getStraat() {
		return straat;
	}

	public String getHuisnr() {
		return huisnr;
	}

	public String getPostcode() {
		return postcode;
	}

	public String getGemeente() {
		return gemeente;
	}

	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	public String getPaswoord() {
		return paswoord;
	}
	
	@Override
	public String toString() {
		return voornaam + " " + familienaam + " " + straat + " " + huisnr + " " + postcode + " " + gemeente;
	}
	
	public static class KlantBuilder {
		private long id;
		private String voornaam;
		private String familienaam;
		private String straat;
		private String huisnr;
		private String postcode;
		private String gemeente;
		private String gebruikersnaam;
		private String paswoord;
		
		public KlantBuilder metId(long id) {
			this.id = id;
			return this;
		}
		
		public KlantBuilder metVoornaam(String voornaam) {
			this.voornaam = voornaam;
			return this;
		}
		
		public KlantBuilder metFamilienaam(String familienaam) {
			this.familienaam = familienaam;
			return this;
		}
		
		public KlantBuilder metStraat(String straat) {
			this.straat = straat;
			return this;
		}
		
		public KlantBuilder metHuisnr(String huisnr) {
			this.huisnr = huisnr;
			return this;
		}
		
		public KlantBuilder metPostcode(String postcode) {
			this.postcode = postcode;
			return this;
		}
		
		public KlantBuilder metGemeente(String gemeente) {
			this.gemeente = gemeente;
			return this;
		}
		
		public KlantBuilder metGebruikersnaam(String gebruikersnaam) {
			this.gebruikersnaam = gebruikersnaam;
			return this;
		}
		
		public KlantBuilder metPaswoord(String paswoord) {
			this.paswoord = paswoord;
			return this;
		}
		
		public Klant maakKlant() {
			return new Klant(id, voornaam, familienaam, straat, huisnr,
					postcode, gemeente, gebruikersnaam, paswoord);
		}
	}
	
}
