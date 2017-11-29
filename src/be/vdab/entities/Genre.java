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
		Genre other = (Genre) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
