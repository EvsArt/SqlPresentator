package com.artevseev.sqlcrudpresentation.data.repository;

import com.artevseev.sqlcrudpresentation.data.model.stat.RestStat;
import com.artevseev.sqlcrudpresentation.data.model.Restaurant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository{

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public Optional<Restaurant> findById(Long id) {

        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from restaurant where id=?")){

            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            return getRestaurantFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Restaurant> findAll() {

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from restaurant");
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
             PreparedStatement statement = connection.prepareStatement("delete from restaurant where id=?"))
        {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Restaurant> save(Restaurant restaurant) {

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO restaurant(name, rating) VALUES(?, ?)"))
        {
            statement.setString(1, restaurant.getName());
            statement.setDouble(2, restaurant.getRating());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return findByName(restaurant.getName());

    }

    @Override
    public Optional<Restaurant> findByName(String name){

        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from restaurant where name=?")){

            statement.setString(1, name);
            resultSet = statement.executeQuery();

            return getRestaurantFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }

    @Override
    public List<Restaurant> findWithoutActiveOrders() {

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(
                     "select * from restaurant " +
                     "where id in " +
                             "(select restaurant_id from \"order\" where not is_finished)"
             );
             ResultSet resultSet = statement.executeQuery()){

            return getListFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    @Override
    public List<RestStat> getTopRestaurants() {

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(
                     "select restaurant.id as id, restaurant.name as name, count(\"order\".id) as count " +
                             "from restaurant " +
                             "inner join \"order\" " +
                             "on restaurant.id = restaurant_id " +
                             "group by restaurant.id, restaurant.name");
             ResultSet resultSet = statement.executeQuery()){

            List<RestStat> restaurantList = new ArrayList<>();
            while (resultSet.next()){
                restaurantList.add(new RestStat(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("count"))
                );
            }
            return restaurantList;

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    private List<Restaurant> getListFromResultSet(ResultSet resultSet) throws SQLException{

        List<Restaurant> restaurantList = new ArrayList<>();
        while (resultSet.next()){
            restaurantList.add(new Restaurant(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getDouble("rating"))
            );
        }
        return restaurantList;

    }

    private Optional<Restaurant> getRestaurantFromResultSet(ResultSet resultSet) throws SQLException{

        Restaurant order = null;
        if (resultSet.next()) {
            order = new Restaurant(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getDouble("rating")
            );
        }
        return Optional.ofNullable(order);

    }

}
