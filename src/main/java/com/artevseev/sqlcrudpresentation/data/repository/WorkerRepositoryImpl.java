package com.artevseev.sqlcrudpresentation.data.repository;

import com.artevseev.sqlcrudpresentation.data.model.Worker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class WorkerRepositoryImpl implements WorkerRepository{

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public Optional<Worker> findById(Long id) {

        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from worker where id=?")){

            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            return getWorkerFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Worker> findAll() {
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from worker");
             ResultSet resultSet = statement.executeQuery()){

            return getListFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteById(Long id) {

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("delete from worker where id=?"))
        {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Worker> save(Worker worker) {

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO worker VALUES(name=?, post_id=?, restaurant_id=?, employment_date=?)"))
        {
            statement.setString(1, worker.getName());
            statement.setInt(2, worker.getPostId());
            statement.setLong(3, worker.getRestaurantId());
            statement.setLong(4, worker.getEmploymentDate().getTime());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return findByName(worker.getName());

    }

    @Override
    public Optional<Worker> findByName(String name){

        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from worker where name=?")){

            statement.setString(1, name);
            resultSet = statement.executeQuery();

            return getWorkerFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Worker> findAllByPostName(String name) {

        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(
                     "select * from worker " +
                             "where post_id in " +
                             "(select id from post where name=?)"
             )){

             statement.setString(1, name);
             resultSet = statement.executeQuery();

            return getListFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    @Override
    public List<Worker> findAllInRestaurant(Long id) {

        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(
                     "select * from worker " +
                             "where restaurant_id = ?"
             )){

            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            return getListFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    private List<Worker> getListFromResultSet(ResultSet resultSet) throws SQLException{

        List<Worker> workerList = new ArrayList<>();
        while (resultSet.next()){
            workerList.add(new Worker(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("post_id"),
                    resultSet.getLong("restaurant_id"),
                    new Date(resultSet.getLong("employment_date")))
            );
        }
        return workerList;
    }

    private Optional<Worker> getWorkerFromResultSet(ResultSet resultSet) throws SQLException{

        Worker worker = null;
        if (resultSet.next()) {
            worker = new Worker(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("post_id"),
                    resultSet.getLong("restaurant_id"),
                    new Date(resultSet.getLong("employment_date"))
            );
        }
        return Optional.ofNullable(worker);

    }

}
