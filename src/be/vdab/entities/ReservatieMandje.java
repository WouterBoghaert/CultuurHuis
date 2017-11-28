package be.vdab.entities;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReservatieMandje implements Serializable {
	private static final long serialVersionUID = 1L;
	private Map<Long,Integer> reservatieMandje = new LinkedHashMap<>();
	
	public void addReservatie(long id, int aantalPlaatsen) {
		reservatieMandje.put(id, aantalPlaatsen);
	}
	
	public void wijzigReservatie(long id, int aantalPlaatsen) {
		reservatieMandje.put(id, aantalPlaatsen);
	}
	
	public void removeById(long id) {
		reservatieMandje.remove(id);
	}	
}
