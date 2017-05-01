package fr.isen.java2.db.mapper;

import fr.isen.java2.db.entities.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaoyuan on 17/02/17.
 */
public class GenreResultMapper implements ResultMapper {
    private List<Genre> generResultMapperList;
    private Integer integer;
    private Genre genre;

    public GenreResultMapper() {
        generResultMapperList = new ArrayList<>();
    }

    @Override
    public List getParsedLIst() throws SQLException {
        return generResultMapperList;
    }

    @Override
    public void parseResultset(ResultSet resultSet) throws SQLException {
        while(resultSet.next()) {
            genre = new Genre(resultSet.getInt("idgenre"),
                    resultSet.getString("name"));
            this.generResultMapperList.add(genre);
        }
    }
}
