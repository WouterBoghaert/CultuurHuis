package vdab.be.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import be.vdab.entities.Klant;

public class KlantRepository extends AbstractRepository {
	private static final String SELECT_BY_GEBRUIKERSNAAM = 
		"select id, voornaam, familienaam, straat, huisnr, postcode, gemeente, "
		+ "gebruikersnaam, paswoord from klanten where gebruikersnaam = ?";
//	private static final String SELECT_ID_BY_GEBRUIKERSNAAM =
//		"select id from klanten where gebruikersnaam = ?";
	private static final String SELECT_BY_ID = 
		"select id, voornaam, familienaam, straat, huisnr, postcode, gemeente, "
		+ "gebruikersnaam, paswoord from klanten where id = ?";
	private static final String KLANT_TOEVOEGEN = 
		"insert into klanten (voornaam, familienaam, straat, huisnr, postcode, "
		+ "gemeente, gebruikersnaam, paswoord) values (?,?,?,?,?,?,?,?)";

	private Klant resultSetRijNaarKlant(ResultSet resultSet) throws SQLException {
		return new Klant.KlantBuilder().metId(resultSet.getLong("id"))
				.metVoornaam(resultSet.getString("voornaam"))
				.metFamilienaam(resultSet.getString("familienaam"))
				.metStraat(resultSet.getString("straat"))
				.metHuisnr(resultSet.getString("huisnr"))
				.metPostcode(resultSet.getString("postcode"))
				.metGemeente(resultSet.getString("gemeente"))
				.metGebruikersnaam(resultSet.getString("gebruikersnaam"))
				.metPaswoord(resultSet.getString("paswoord"))
				.maakKlant();
	}
	
	public Optional<Klant> getKlantByGebruikersnaam(String gebruikersnaam) {
		try(Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_BY_GEBRUIKERSNAAM)) {
			Optional<Klant> klant;
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setString(1, gebruikersnaam);
			try(ResultSet resultSet = statement.executeQuery()) {
				if(resultSet.next()) {
					klant = Optional.of(resultSetRijNaarKlant(resultSet));
				}
				else {
					klant = Optional.empty();
				}
			}
			connection.commit();
			return klant;
		}
		catch(SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
	
	public Optional<Klant> getKlantById(long id) {
		try(Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
			Optional<Klant> klant;
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setLong(1, id);
			try(ResultSet resultSet = statement.executeQuery()) {
				if(resultSet.next()) {
					klant = Optional.of(resultSetRijNaarKlant(resultSet));
				}
				else {
					klant = Optional.empty();
				}
			}
			connection.commit();
			return klant;
		}
		catch(SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
	
	// als gebruikersnaam al bestaat => return false, als toegevoegd, return true
	public boolean klantToevoegen(Klant klant) {
		try(Connection connection = dataSource.getConnection();
			PreparedStatement statementSelect = connection.prepareStatement(SELECT_BY_GEBRUIKERSNAAM);
			PreparedStatement statementInsert = connection.prepareStatement(KLANT_TOEVOEGEN)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			connection.setAutoCommit(false);
			statementSelect.setString(1, klant.getGebruikersnaam());
			try (ResultSet resultSet = statementSelect.executeQuery()) {
				if (resultSet.next()) {
					return false;
				}
				else {
					statementInsert.setString(1, klant.getVoornaam());
					statementInsert.setString(2, klant.getFamilienaam());
					statementInsert.setString(3, klant.getStraat());
					statementInsert.setString(4, klant.getHuisnr());
					statementInsert.setString(5, klant.getPostcode());
					statementInsert.setString(6, klant.getGemeente());
					statementInsert.setString(7, klant.getGebruikersnaam());
					statementInsert.setString(8, klant.getPaswoord());
					statementInsert.executeUpdate();
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
