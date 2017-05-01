package fr.isen.java2.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ResultMapper<T> {
    public abstract List<T> getParsedLIst() throws SQLException;
    public abstract void parseResultset(ResultSet resultSet) throws SQLException;
}
