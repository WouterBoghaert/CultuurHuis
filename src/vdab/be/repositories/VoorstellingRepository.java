package vdab.be.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Optional;
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
		+ "where genreid = ? && datum >= {fn now()} order by datum, id";
	private static final String SELECT_BY_ID = 
		BEGIN_SELECT
		+ "where voorstellingen.id = ?";
	private static final String SELECT_BY_IDS = 
		BEGIN_SELECT
		+ "where voorstellingen.id in (";
	private static final String VRIJEPLAATSEN_VERMINDEREN = 
		"update voorstellingen set vrijeplaatsen = vrijeplaatsen - ? "
		+ "where id = ?";
	
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
	
	public Set<Voorstelling> toonAlleVoorGenre(long genreId) {
		try(Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_ALL_GENRE)) {
			Set<Voorstelling> voorstellingen = new LinkedHashSet<>();
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setInt(1, (int)(genreId));
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
	
	public Optional<Voorstelling> selectById(long id) {
		try(Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
			Optional<Voorstelling> voorstelling;
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setInt(1, (int) id);
			try(ResultSet resultSet = statement.executeQuery()) {
				if(resultSet.next()) {
					voorstelling = Optional.of(resultSetNaarVoorstelling(resultSet));
				}
				else {
					voorstelling = Optional.empty();
				}
			}
			connection.commit();
			return voorstelling;
		}
		catch(SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
	
	public Set<Voorstelling> selectByIds(Set<Long> ids) {
		StringBuilder selectBuilder = new StringBuilder();
		selectBuilder.append(SELECT_BY_IDS);
		ids.stream().forEach(id -> selectBuilder.append("?,"));
		selectBuilder.setCharAt(selectBuilder.length() - 1, ')');
		selectBuilder.append(" order by datum, id");
		try(Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(selectBuilder.toString())) {
			Set<Voorstelling> voorstellingen = new LinkedHashSet<>();
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			int index = 0;
			for (long id : ids) {
				statement.setInt(++index, (int) id);
			}
			try(ResultSet resultSet = statement.executeQuery()) {
				while(resultSet.next()) {
					voorstellingen.add(resultSetNaarVoorstelling(resultSet));
				}
			}
			connection.commit();
			return voorstellingen;
		}
		catch(SQLException ex) {
			throw new RepositoryException(ex);
		}	
	}
	
	public boolean vrijePlaatsenVerminderen(long id, int aantalPlaatsen) {
		try(Connection connection = dataSource.getConnection();
			PreparedStatement statementSelect = connection.prepareStatement(SELECT_BY_ID);
			PreparedStatement statementUpdate = connection.prepareStatement(VRIJEPLAATSEN_VERMINDEREN)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			connection.setAutoCommit(false);
			statementSelect.setInt(1, (int) id);
			try(ResultSet resultSet = statementSelect.executeQuery()) {
				if(resultSet.next()) {
					if(aantalPlaatsen <= resultSet.getInt("vrijeplaatsen")) {
						statementUpdate.setInt(1, aantalPlaatsen);
						statementUpdate.setInt(2, (int) id);
						statementUpdate.executeUpdate();
					}
					else {
						return false;
					}
				}
			}
			connection.commit();
			return true;
		}
		catch(SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
}
