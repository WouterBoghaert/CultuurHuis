package vdab.be.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import be.vdab.entities.Genre;
import be.vdab.entities.Voorstelling;

public class VoorstellingRepository extends AbstractRepository {
	private static final String BEGIN_SELECT =
		"select voorstellingen.id as id, titel, uitvoerders, "
		+ "datum, genreid, prijs, vrijeplaatsen, naam as genrenaam "
		+ "from voorstellingen inner join genres on genres.id = voorstellingen.genreid ";
	private static final String SELECT_ALL_GENRE = 
		BEGIN_SELECT
		+ "where genreid = ? && datum >= {fn now()} order by datum";
	private static final String SELECT_BY_ID = 
		BEGIN_SELECT
		+ "where voorstellingen.id = ?";
	private static final String SELECT_BY_IDS = 
		BEGIN_SELECT
		+ "where voorstellingen.id in (";
	private static final String VRIJEPLAATSEN_VERMINDEREN = 
		"update voorstellingen set vrijeplaatsen = vrijeplaatsen - ? "
		+ "where id = ?";
	
	// enkel selectie van voorstellingen  (datum < systeemdatum)!!! nodig bij genres
	//bij vrije plaatsen verminderen opnieuw checken of plaatsen beschikbaar zijn 
	//en resultaat doorgeven met boolean, plaatsen en Voorstelling nodig
	
	private Voorstelling resultSetNaarVoorstelling(ResultSet resultSet) throws SQLException {
		return new Voorstelling.VoorstellingBuilder()
				.metId((long)(resultSet.getInt("id")))
				.metTitel(resultSet.getString("titel"))
				.metUitvoerders(resultSet.getString("uitvoerders"))
				.metDatum(resultSet.getTimestamp("datum").toLocalDateTime())
				.metGenre(new Genre((long)(resultSet.getInt("genreid")), resultSet.getString("genrenaam")))
				.metPrijs(resultSet.getBigDecimal("prijs"))
				.metAantalVrijePlaatsen(resultSet.getInt("vrijeplaatsen"))
				.maakVoorstelling();
	}
	
	public Set<Voorstelling> toonAlleVoorGenre(Genre genre) {
		try(Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_ALL_GENRE)) {
			Set<Voorstelling> voorstellingen = new LinkedHashSet<>();
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setInt(1, (int)(genre.getId()));
			try(ResultSet resultSet = statement.executeQuery()) {
				while(resultSet.next()) {
					voorstellingen.add(resultSetNaarVoorstelling(resultSet));
				}
			}
			connection.commit();
			return voorstellingen;
		}
		catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
}
