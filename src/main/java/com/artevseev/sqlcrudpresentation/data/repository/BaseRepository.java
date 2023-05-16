package com.artevseev.sqlcrudpresentation.data.repository;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, ID> {

    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
    Optional<T> save(T obj);

    default void deleteAll(String dbUrl, String login, String password, String tableName){
        try (Connection connection = DriverManager.getConnection(dbUrl, login, password);
             PreparedStatement statement = connection.prepareStatement("delete from " + tableName)) {

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
