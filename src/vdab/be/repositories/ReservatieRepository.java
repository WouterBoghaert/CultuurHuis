package vdab.be.repositories;

public class ReservatieRepository extends AbstractRepository {
	private static final String SELECT_BY_ID =
		"select datum, titel, uitvoerders, prijs, plaatsen, vrijeplaatsen "+
		"from reservaties inner join voorstellingen "
		+ "on reservaties.voorstellingsid = voorstellingen.id " +
		"where reservaties.id = ?";
	private static final String RESERVATIE_TOEVOEGEN =
		"insert into reservaties(id, klantid, voorstellingsid, plaatsen) "
		+ "values (?,?,?,?)";
	
	public 
			
}
