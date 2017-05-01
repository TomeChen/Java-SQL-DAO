package fr.isen.java2.db.daos.impl;

import fr.isen.java2.db.daos.MovieDao;
import fr.isen.java2.db.entities.Genre;
import fr.isen.java2.db.entities.Movie;
import fr.isen.java2.db.mapper.MovieResultMapper;
import fr.isen.java2.db.util.QueryExecutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaoyuan on 13/02/17.
 */
public class MovieDaoImpl implements MovieDao {
    private MovieResultMapper movieResultMapper = new MovieResultMapper();

    @Override
    public List<Movie> listMovies() throws Exception {
        String sqlQuery = "SELECT * FROM movie JOIN genre ON movie.genre_id=genre.idgenre";
        QueryExecutor.executeSelectQuery(sqlQuery,movieResultMapper);
        return this.movieResultMapper.getParsedLIst();
    }

    @Override
    public List<Movie> listMoviesByGenre(String genreName) throws Exception {
        String sqlQuery = "SELECT * FROM movie JOIN genre ON movie.genre_id=genre.idgenre WHERE genre.name=?";
        QueryExecutor.executeSelectQuery(sqlQuery,movieResultMapper,genreName);
        return this.movieResultMapper.getParsedLIst();
    }

    @Override
    public void addMovie(Movie movie) throws Exception {
        String sqlQuery = "INSERT INTO movie(title,release_date,genre_id,duration,director,summary) VALUES(?,?,?,?,?,?)";
        QueryExecutor.executeUpdateQuery(sqlQuery,
                movie.getTitle(),
                movie.getReleaseDate().toString(),
                movie.getGenre().getId().toString(),
                movie.getDuration().toString(),
                movie.getDirector(),
                movie.getSummary());
    }
}
