package fr.isen.java2.db.daos.impl;

import fr.isen.java2.db.daos.GenreDao;
import fr.isen.java2.db.entities.Genre;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class GenreDaoImplTestCase {

	private GenreDao genreDao;

	@Before   //初始化。 插入 3 个东西
	public void initDatabase() throws Exception {
	    genreDao = new GenreDaoImpl();
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("DELETE FROM movie");
		stmt.executeUpdate("DELETE FROM genre");
		stmt.executeUpdate("INSERT INTO genre(idgenre,name) VALUES (1,'Drama')");
		stmt.executeUpdate("INSERT INTO genre(idgenre,name) VALUES (2,'Comedy')");
		stmt.executeUpdate("INSERT INTO genre(idgenre,name) VALUES (3,'Thriller')");
		stmt.close();
		connection.close();
	}

	@Test //初始化完后，程序最先运行这个程序。可能是因为是 @test 的原因
	public void shouldListGenres() throws Exception {
		// WHEN
		List<Genre> genres = genreDao.listGenres();
		// THEN
		assertThat(genres).hasSize(3);
		assertThat(genres).extracting("id", "name").containsOnly(tuple(1, "Drama"), tuple(2, "Comedy"),
				tuple(3, "Thriller"));
	}
	
	@Test
	public void shouldGetGenreByName() throws Exception {
		// WHEN
		Genre genre = genreDao.getGenre("Comedy");
		// THEN
		assertThat(genre.getId()).isEqualTo(2);
		assertThat(genre.getName()).isEqualTo("Comedy");
	}
	
	@Test
	public void shouldGetNotGenreByNameNutNull() throws Exception {
		// WHEN
		Genre genre = genreDao.getGenre("Inexistant");
		// THEN
		assertThat(genre).isNull();
	}
	
	@Test
	public void shouldAddGenre() throws Exception {
		// WHEN 
		genreDao.addGenre("Western");
		// THEN
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM genre WHERE name='Western'");
		assertThat(resultSet.next()).isTrue();
		assertThat(resultSet.getInt("idgenre")).isNotNull();
		assertThat(resultSet.getString("name")).isEqualTo("Western");
		assertThat(resultSet.next()).isFalse();
		resultSet.close();
		statement.close();
		connection.close();
	}
}