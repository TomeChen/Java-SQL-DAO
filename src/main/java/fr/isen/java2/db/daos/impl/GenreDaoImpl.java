package fr.isen.java2.db.daos.impl;

import fr.isen.java2.db.daos.GenreDao;
import fr.isen.java2.db.entities.Genre;
import fr.isen.java2.db.mapper.GenreResultMapper;
import fr.isen.java2.db.util.QueryExecutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaoyuan on 13/02/17.
 */
public class GenreDaoImpl implements GenreDao {
    private Connection connection;

    public GenreDaoImpl() throws Exception {
        this.connection = DataSourceFactory.getDataSource().getConnection();
    }

    @Override
    public List<Genre> listGenres() throws Exception {
        GenreResultMapper genreResultMapper = new GenreResultMapper();
        String sqlQuery = "SELECT * FROM genre";
        QueryExecutor.executeSelectQuery(sqlQuery,genreResultMapper);
        return genreResultMapper.getParsedLIst();
    }

    @Override
    public Genre getGenre(String name) throws Exception {
        GenreResultMapper genreResultMapper = new GenreResultMapper();
        String sqlQuery = "SELECT * FROM genre WHERE name = ?";
        QueryExecutor.executeSelectQuery(sqlQuery,genreResultMapper,name);
        return genreResultMapper.getParsedLIst().isEmpty()? null: (Genre) genreResultMapper.getParsedLIst().get(0);
        }

    @Override
    public void addGenre(String name) throws Exception {
        GenreResultMapper genreResultMapper = new GenreResultMapper();
        String genreQuery = "INSERT INTO genre(name) VALUES(?)";
        QueryExecutor.executeUpdateQuery(genreQuery,name);
    }
}
