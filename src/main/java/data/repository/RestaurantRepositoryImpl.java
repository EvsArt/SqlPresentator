package data.repository;

import data.model.Restaurant;
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

    @Override
    public Optional<Restaurant> findById(Long id) {

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = connection.prepareStatement("select * from restaurant where id=?");
             ResultSet resultSet = statement.executeQuery()){

            statement.setLong(1, id);

            Restaurant order = null;
            if (resultSet.next()) {
                order = new Restaurant(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("rating")
                );
            }
            return Optional.ofNullable(order);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Restaurant> findAll() {
        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = connection.prepareStatement("select * from restaurant");
             ResultSet resultSet = statement.executeQuery()){

            List<Restaurant> restaurantList = new ArrayList<>();
            while (resultSet.next()){
                restaurantList.add(new Restaurant(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("rating"))
                );
            }
            return restaurantList;

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteById(Long id) {

        try (Connection connection = DriverManager.getConnection(dbUrl);
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

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO restaurant VALUES(name=?, rating=?)"))
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

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = connection.prepareStatement("select * from restaurant where name=?");
             ResultSet resultSet = statement.executeQuery()){

            statement.setString(1, name);

            Restaurant restaurant = null;
            if (resultSet.next()) {
                restaurant = new Restaurant(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("rating")
                );
            }
            return Optional.ofNullable(restaurant);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }

}
