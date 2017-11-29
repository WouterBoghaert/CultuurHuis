package vdab.be.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import be.vdab.entities.Reservatie;

public class ReservatieRepository extends AbstractRepository {
	private static final String RESERVATIE_TOEVOEGEN =
		"insert into reservaties(klantid, voorstellingsid, plaatsen) "
		+ "values (?,?,?)";
	
	public void reservatieToevoegen(Reservatie reservatie) {
		try(Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(RESERVATIE_TOEVOEGEN,
			Statement.RETURN_GENERATED_KEYS)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			connection.setAutoCommit(false);
			statement.setInt(1, (int)(reservatie.getKlantId()));
			statement.setInt(2, (int)(reservatie.getVoorstellingId()));
			statement.setInt(3, reservatie.getAantalPlaatsen());
			statement.executeUpdate();
			try(ResultSet resultSet = statement.getGeneratedKeys()) {
				resultSet.next();
				reservatie.setId((long)(resultSet.getInt("id")));
			}
			connection.commit();
		}
		catch(SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
			
}
