package vdab.be.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

import be.vdab.entities.Genre;

public class GenreRepository extends AbstractRepository {
	private static final String SELECT_ALL =
		"select id, naam from genres order by naam";
	
	public Set<Genre> toonAlleGenres() {
		try(Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement()) {
			Set<Genre> genres = new LinkedHashSet<>();
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			try(ResultSet resultSet = statement.executeQuery(SELECT_ALL)) {
				while(resultSet.next()) {
					genres.add(new Genre((long)(resultSet.getInt("id")),resultSet.getString("naam")));
				}
			}
			connection.commit();
			return genres;
		}
		catch(SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
}
