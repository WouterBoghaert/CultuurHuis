package vdab.be.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.vdab.entities.Reservatie;

public class ReservatieRepository extends AbstractRepository {
//	private static final String RESERVATIE_TOEVOEGEN =
//		"insert into reservaties(klantid, voorstellingsid, plaatsen) "
//		+ "values (?,?,?)";
	
	private static final String RESERVATIES_TOEVOEGEN =
			"insert into reservaties(klantid, voorstellingsid, plaatsen) "
			+ "values ";
	
	private static final Logger LOGGER =
		Logger.getLogger(ReservatieRepository.class.getName());
	
//	public void reservatieToevoegen(Reservatie reservatie) {
//		try(Connection connection = dataSource.getConnection();
//			PreparedStatement statement = connection.prepareStatement(RESERVATIE_TOEVOEGEN,
//			Statement.RETURN_GENERATED_KEYS)) {
//			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
//			connection.setAutoCommit(false);
//			statement.setInt(1, (int)(reservatie.getKlantId()));
//			statement.setInt(2, (int)(reservatie.getVoorstellingId()));
//			statement.setInt(3, reservatie.getAantalPlaatsen());
//			statement.executeUpdate();
//			try(ResultSet resultSet = statement.getGeneratedKeys()) {
//				resultSet.next();
//				reservatie.setId((long)(resultSet.getInt("id")));
//			}
//			connection.commit();
//		}
//		catch(SQLException ex) {
//			throw new RepositoryException(ex);
//		}
//	}
	
	public void reservatiesToevoegen(Set<Reservatie> reservaties) {
		StringBuilder builder = new StringBuilder();
		builder.append(RESERVATIES_TOEVOEGEN);
		reservaties.forEach(reservatie -> builder.append("(?,?,?),"));
		builder.deleteCharAt(builder.length()-1);
		String reservatiesToevoegen = builder.toString();
		try(Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(reservatiesToevoegen,
			Statement.RETURN_GENERATED_KEYS)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			connection.setAutoCommit(false);
			int index = 0;
			for(Reservatie reservatie : reservaties) {
				statement.setInt(++index, (int)(reservatie.getKlantId()));
				statement.setInt(++index, (int)(reservatie.getVoorstellingId()));
				statement.setInt(++index, reservatie.getAantalPlaatsen());
			}
			statement.executeUpdate();
			try(ResultSet resultSet = statement.getGeneratedKeys()) {
				index = 0;
				Reservatie [] reservatieArray = new Reservatie [reservaties.size()];
				for(Reservatie reservatie: reservaties) {
					resultSet.next();
					reservatie.setId((long)(resultSet.getInt(1)));
					reservatieArray[index++] = reservatie;
				}
			}
			connection.commit();
		}
		catch(SQLException ex) {
			LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
			throw new RepositoryException(ex);
		}		
	}			
}
