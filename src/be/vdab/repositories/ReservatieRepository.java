package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.vdab.entities.Reservatie;

public class ReservatieRepository extends AbstractRepository {
//	private static final String RESERVATIE_TOEVOEGEN =
//		"insert into reservaties(klantid, voorstellingsid, plaatsen) "
//		+ "values (?,?,?)";
	
	private static final String SELECT_VRIJEPLAATSEN_BY_ID = 
			"select vrijeplaatsen from voorstellingen where id = ?";
	
	private static final String VRIJEPLAATSEN_VERMINDEREN = 
			"update voorstellingen set vrijeplaatsen = vrijeplaatsen - ? "
			+ "where id = ?";
	
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
	
//	public void reservatiesToevoegen(Set<Reservatie> reservaties) {
//		StringBuilder builder = new StringBuilder();
//		builder.append(RESERVATIES_TOEVOEGEN);
//		reservaties.forEach(reservatie -> builder.append("(?,?,?),"));
//		builder.deleteCharAt(builder.length()-1);
//		String reservatiesToevoegen = builder.toString();
//		try(Connection connection = dataSource.getConnection();
//			PreparedStatement statement = connection.prepareStatement(reservatiesToevoegen,
//			Statement.RETURN_GENERATED_KEYS)) {
//			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
//			connection.setAutoCommit(false);
//			int index = 0;
//			for(Reservatie reservatie : reservaties) {
//				statement.setInt(++index, (int)(reservatie.getKlantId()));
//				statement.setInt(++index, (int)(reservatie.getVoorstellingId()));
//				statement.setInt(++index, reservatie.getAantalPlaatsen());
//			}
//			statement.executeUpdate();
//			try(ResultSet resultSet = statement.getGeneratedKeys()) {
//				index = 0;
//				Reservatie [] reservatieArray = new Reservatie [reservaties.size()];
//				for(Reservatie reservatie: reservaties) {
//					resultSet.next();
//					reservatie.setId((long)(resultSet.getInt(1)));
//					reservatieArray[index++] = reservatie;
//				}
//			}
//			connection.commit();
//		}
//		catch(SQLException ex) {
//			LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
//			throw new RepositoryException(ex);
//		}		
//	}
	
	
	public Map<String,Map<Long,Integer>> reservatiesToevoegen(Set<Reservatie> reservaties) {
		Map<Long,Integer> gelukt = new LinkedHashMap<>();
		Map<Long,Integer> mislukt = new LinkedHashMap<>();
		Set<Reservatie>	gelukteReservaties = new LinkedHashSet<>();
		Set<Reservatie>	mislukteReservaties = new LinkedHashSet<>();
		Map<String,Map<Long,Integer>> output = new LinkedHashMap<>();
		StringBuilder builder = new StringBuilder();
		builder.append(RESERVATIES_TOEVOEGEN);
		
		try(Connection connection = dataSource.getConnection();
			PreparedStatement statementSelect = connection.prepareStatement(SELECT_VRIJEPLAATSEN_BY_ID);
			PreparedStatement statementUpdate = connection.prepareStatement(VRIJEPLAATSEN_VERMINDEREN)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			connection.setAutoCommit(false);
			
			for (Reservatie reservatie : reservaties) {
				statementSelect.setInt(1, (int) reservatie.getVoorstellingId());
				try(ResultSet resultSet = statementSelect.executeQuery()) {
					if(resultSet.next()) {
						if(reservatie.getAantalPlaatsen() <= resultSet.getInt("vrijeplaatsen")) {
							statementUpdate.setInt(1, reservatie.getAantalPlaatsen());
							statementUpdate.setInt(2, (int) reservatie.getVoorstellingId());
							statementUpdate.executeUpdate();
							builder.append("(?,?,?),");
							gelukteReservaties.add(reservatie);							
						}
						else {
							mislukteReservaties.add(reservatie);
						}
					}
				}
			}
			
			builder.deleteCharAt(builder.length()-1);
			String reservatiesToevoegen = builder.toString();
			try(PreparedStatement statementReservaties = connection.prepareStatement(reservatiesToevoegen,
				Statement.RETURN_GENERATED_KEYS)) {
				int index = 0;
				for(Reservatie reservatie : gelukteReservaties) {
					statementReservaties.setInt(++index, (int)(reservatie.getKlantId()));
					statementReservaties.setInt(++index, (int)(reservatie.getVoorstellingId()));
					statementReservaties.setInt(++index, reservatie.getAantalPlaatsen());
					gelukt.put(reservatie.getVoorstellingId(), reservatie.getAantalPlaatsen());
				}
				statementReservaties.executeUpdate();
				try(ResultSet resultSet = statementReservaties.getGeneratedKeys()) {
					index = 0;
					Reservatie [] reservatieArray = new Reservatie [reservaties.size()];
					for(Reservatie reservatie: reservaties) {
						resultSet.next();
						reservatie.setId((long)(resultSet.getInt(1)));
						reservatieArray[index++] = reservatie;
					}
				}
			}		
			connection.commit();
			mislukteReservaties.stream().forEach(reservatie -> mislukt.put(reservatie.getVoorstellingId(), reservatie.getAantalPlaatsen()));
			output.put("gelukt", gelukt);
			output.put("mislukt", mislukt);
			return output;
		}
		catch(SQLException ex) {
			LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
			throw new RepositoryException(ex);
		}
	}
		
//		StringBuilder builder = new StringBuilder();
//		builder.append(RESERVATIES_TOEVOEGEN);
//		reservaties.forEach(reservatie -> builder.append("(?,?,?),"));
//		builder.deleteCharAt(builder.length()-1);
//		String reservatiesToevoegen = builder.toString();
//		try(Connection connection = dataSource.getConnection();
//			PreparedStatement statement = connection.prepareStatement(reservatiesToevoegen,
//			Statement.RETURN_GENERATED_KEYS)) {
//			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
//			connection.setAutoCommit(false);
//			int index = 0;
//			for(Reservatie reservatie : reservaties) {
//				statement.setInt(++index, (int)(reservatie.getKlantId()));
//				statement.setInt(++index, (int)(reservatie.getVoorstellingId()));
//				statement.setInt(++index, reservatie.getAantalPlaatsen());
//			}
//			statement.executeUpdate();
//			try(ResultSet resultSet = statement.getGeneratedKeys()) {
//				index = 0;
//				Reservatie [] reservatieArray = new Reservatie [reservaties.size()];
//				for(Reservatie reservatie: reservaties) {
//					resultSet.next();
//					reservatie.setId((long)(resultSet.getInt(1)));
//					reservatieArray[index++] = reservatie;
//				}
//			}
//			connection.commit();
//		}
//		catch(SQLException ex) {
//			LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
//			throw new RepositoryException(ex);
//		}		
//	}
//	
//	for(Entry<Long,Integer> entry:reservatiemandje.entrySet()) {
//		if(voorstellingRepository.vrijePlaatsenVerminderen(entry.getKey(), entry.getValue())) {
//			gelukt.put(entry.getKey(), entry.getValue());
//		}
//		else {
//			mislukt.put(entry.getKey(), entry.getValue());
//		}
//	}
//	
//	public boolean vrijePlaatsenVerminderen(long id, int aantalPlaatsen) {
//		try(Connection connection = dataSource.getConnection();
//			PreparedStatement statementSelect = connection.prepareStatement(SELECT_BY_ID);
//			PreparedStatement statementUpdate = connection.prepareStatement(VRIJEPLAATSEN_VERMINDEREN)) {
//			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
//			connection.setAutoCommit(false);
//			statementSelect.setInt(1, (int) id);
//			try(ResultSet resultSet = statementSelect.executeQuery()) {
//				if(resultSet.next()) {
//					if(aantalPlaatsen <= resultSet.getInt("vrijeplaatsen")) {
//						statementUpdate.setInt(1, aantalPlaatsen);
//						statementUpdate.setInt(2, (int) id);
//						statementUpdate.executeUpdate();
//					}
//					else {
//						return false;
//					}
//				}
//			}
//			connection.commit();
//			return true;
//		}
}
