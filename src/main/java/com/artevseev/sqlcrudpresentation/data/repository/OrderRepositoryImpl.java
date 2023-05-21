package com.artevseev.sqlcrudpresentation.data.repository;

import com.artevseev.sqlcrudpresentation.data.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository{

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public Optional<Order> findById(Long id) {

        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from \"order\" where id=?")){

            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            return getOrderFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Order> findAll() {

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from \"order\"");
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
             PreparedStatement statement = connection.prepareStatement("delete from \"order\" where id=?"))
        {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Order> save(Order order) {

        Date date = new Date();

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO \"order\"(type, comment, order_time, is_finished, restaurant_id) VALUES(?, ?, ?, ?, ?)"))
        {
            statement.setInt(1, order.getType());
            statement.setString(2, order.getComment());
            statement.setTimestamp(3, (order.getOrderTime() != null) ?
                    new java.sql.Timestamp(order.getOrderTime().getTime()) : new java.sql.Timestamp(date.getTime()));
            statement.setBoolean(4, (order.getIsFinished() != null) ? order.getIsFinished() : false);
            statement.setLong(5, order.getRestaurantId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return findByOrderTime((order.getOrderTime() != null) ? order.getOrderTime() : date);

    }

    private Optional<Order> findByOrderTime(java.util.Date orderTime){

        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(
                     "select * from \"order\" where order_time=?"
             )){

            statement.setTimestamp(1, new Timestamp(orderTime.getTime()));
            resultSet = statement.executeQuery();

            return getOrderFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }

    @Override
    public List<Order> findUnfinishedOrders(){

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from \"order\" where not is_finished");
             ResultSet resultSet = statement.executeQuery()){

            return getListFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    @Override
    public List<Order> findFinishedOrders(){

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from \"order\" where is_finished");
             ResultSet resultSet = statement.executeQuery()){

             return getListFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    @Override
    public List<Order> findAllByFoodName(String name) {

        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(
                     "select * from \"order\"" +
                     "where type in (select id from food where name=?)"
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
    public Optional<Order> finish(Long id) {

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("update \"order\"" +
                     "set is_finished = true " +
                     "where id=?");
             PreparedStatement statement1 = connection.prepareStatement("select * from \"order\" where id=?"))
        {

            statement.setLong(1, id);
            statement.executeUpdate();

            statement1.setLong(1, id);
            return getOrderFromResultSet(statement1.executeQuery());

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private List<Order> getListFromResultSet(ResultSet resultSet) throws SQLException{

        List<Order> orderList = new ArrayList<>();
        while (resultSet.next()){
            java.sql.Timestamp dateFromRaw = resultSet.getTimestamp("order_time");
            orderList.add(new Order(
                    Long.parseLong(resultSet.getString("id")),
                    Integer.parseInt(resultSet.getString("type")),
                    resultSet.getString("comment"),
                    (dateFromRaw != null) ?
                            new Timestamp(dateFromRaw.getTime()) : null,
                    resultSet.getBoolean("is_finished"),
                    resultSet.getLong("restaurant_id"))
            );
        }
        return orderList;
    }

    private Optional<Order> getOrderFromResultSet(ResultSet resultSet) throws SQLException{

        Order order = null;
        if (resultSet.next()) {
            java.sql.Timestamp dateFromRaw = resultSet.getTimestamp("order_time");
            order = new Order(
                    Long.parseLong(resultSet.getString("id")),
                    Integer.parseInt(resultSet.getString("type")),
                    resultSet.getString("comment"),
                    (dateFromRaw != null) ?
                            new Timestamp(dateFromRaw.getTime()) : null,
                    resultSet.getBoolean("is_finished"),
                    resultSet.getLong("restaurant_id")
            );
        }
        return Optional.ofNullable(order);

    }

}
