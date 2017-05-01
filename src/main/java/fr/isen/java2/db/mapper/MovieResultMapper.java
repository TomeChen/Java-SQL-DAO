package fr.isen.java2.db.mapper;

import fr.isen.java2.db.entities.Genre;
import fr.isen.java2.db.entities.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaoyuan on 17/02/17.
 */
public class MovieResultMapper implements ResultMapper {
    private List<Movie> movieResultMapperList;
    private Movie movie;

    public MovieResultMapper() {
        movieResultMapperList = new ArrayList<>();
    }

    @Override
    public List getParsedLIst() throws SQLException {
        return movieResultMapperList;
    }

    @Override
    public void parseResultset(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            movie = new Movie(
                    resultSet.getInt("idmovie"),
                    resultSet.getString("title"),
                    resultSet.getDate("release_date").toLocalDate(),
                    new Genre(resultSet.getInt("idgenre"), resultSet.getString("name")),
                    resultSet.getInt("duration"),
                    resultSet.getString("director"),
                    resultSet.getString("summary")
            );
            this.movieResultMapperList.add(movie);
        }
    }
}
