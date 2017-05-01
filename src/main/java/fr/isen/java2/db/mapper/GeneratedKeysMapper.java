package fr.isen.java2.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by liaoyuan on 17/02/17.
 */
public class GeneratedKeysMapper implements ResultMapper<Integer> {
    List<Integer> keyMapperList;

    @Override
    public List getParsedLIst() throws SQLException {
        return this.keyMapperList;
    }

    @Override
    public void parseResultset(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            this.keyMapperList.add(resultSet.getInt("idgenre"));
        }
    }
}
