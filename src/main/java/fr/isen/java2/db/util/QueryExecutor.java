package fr.isen.java2.db.util;

import fr.isen.java2.db.daos.impl.DataSourceFactory;
import fr.isen.java2.db.mapper.GeneratedKeysMapper;
import fr.isen.java2.db.mapper.ResultMapper;

import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

public class QueryExecutor {
    public static Connection connection;
    public static Statement stmt;


    public static void executeSelectQuery(String query, ResultMapper mapper, Object... parameters) throws Exception {
        connection = DataSourceFactory.getDataSource().getConnection();
        stmt = connection.createStatement();
        int pIndex = 1;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (Object parameter : parameters) {
            preparedStatement.setString(pIndex, parameter.toString());
            pIndex++;
        }
        try(ResultSet resultSet = preparedStatement.executeQuery()) {
            mapper.parseResultset(resultSet);
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("resultSet doesn't work in the executeSelectQuery mehotd of QueryExecutor class! : return null");
        }
    }

    public static ResultMapper executeUpdateQuery(String query, Object... parameters) throws Exception {
        connection = DataSourceFactory.getDataSource().getConnection();
        stmt = connection.createStatement();
        ResultMapper generatedKeysMapper = new GeneratedKeysMapper();
        int pIndex = 1;
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        for (Object parameter : parameters) {
            preparedStatement.setString(pIndex, parameter.toString());
            pIndex++;
        }
        preparedStatement.executeUpdate();
        try (ResultSet resultSet = preparedStatement.getGeneratedKeys()){
            generatedKeysMapper.parseResultset(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("resultSet doesn't work in the executeUpdateQuery mehotd of QueryExecutor class!: return null");
        }
        return generatedKeysMapper;
    }


    private static void mapParameters(final PreparedStatement statement, final Object[] parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            Object currentParameter = parameters[i];
            if (currentParameter instanceof String) {
                statement.setString(i + 1, String.valueOf(currentParameter));
            }
            if (currentParameter instanceof Integer) {
                statement.setInt(i + 1, (Integer) currentParameter);
            }
            if(currentParameter instanceof LocalDate){
                statement.setObject(i+1,currentParameter, Types.DATE);
            }
        }
    }
}
