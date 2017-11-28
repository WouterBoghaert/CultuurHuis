package be.vdab.entities;

import be.vdab.util.IntLongUtils;
import be.vdab.util.StringUtils;

public class Genre {
	private long id;
	private String naam;
	
	public Genre (long id, String naam) {
		if (IntLongUtils.isStrictPositief(id)) {
			this.id = id;
		}
		else {
			throw new IllegalArgumentException();
		}
		if(StringUtils.isNotEmpty(naam)) {
			this.naam = naam;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public long getId() {
		return id;
	}

	public String getNaam() {
		return naam;
	}
	
	@Override
	public String toString() {
		return naam;
	}
}
