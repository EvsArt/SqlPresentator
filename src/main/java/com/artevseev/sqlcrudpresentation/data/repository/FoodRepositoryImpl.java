package com.artevseev.sqlcrudpresentation.data.repository;

import com.artevseev.sqlcrudpresentation.data.model.Food;
import com.artevseev.sqlcrudpresentation.data.model.stat.FoodStat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FoodRepositoryImpl implements FoodRepository {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public Optional<Food> findById(Integer id) {

        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
                PreparedStatement statement = connection.prepareStatement("select * from food where id=?")) {

            statement.setString(1, String.valueOf(id));
            resultSet = statement.executeQuery();

            return getFoodFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Food> findAll() {
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from food");
             ResultSet resultSet = statement.executeQuery()){

            return getListFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteById(Integer id) {

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("delete from food where id=?")) {

            statement.setString(1, String.valueOf(id));
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Food> save(Food food) {

            try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO food(name) VALUES(?)"
                 ))
            {

                statement.setString(1, food.getName());
                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return findByName(food.getName());

    }

    @Override
    public Optional<Food> findByName(String name) {

        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from food where name=?")){

            statement.setString(1, name);
            resultSet = statement.executeQuery();

            return getFoodFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<String> findAllNames() {

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("select name from food");
             ResultSet resultSet = statement.executeQuery()){

            List<String> names = new ArrayList<>();
            if (resultSet.next()) {
                names.add(resultSet.getString("name"));
            }

            return names;

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    @Override
    public List<FoodStat> getTopFood() {

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(
                     "select food.id as id, food.name as name, count(\"order\".id) as count " +
                             "from food " +
                             "inner join \"order\" " +
                             "on food.id = type " +
                             "group by food.id, food.name");
             ResultSet resultSet = statement.executeQuery()){

            List<FoodStat> foodList = new ArrayList<>();
            while (resultSet.next()){
                foodList.add(new FoodStat(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("count"))
                );
            }
            return foodList;

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    private List<Food> getListFromResultSet(ResultSet resultSet) throws SQLException{

        List<Food> foodList = new ArrayList<>();
        while (resultSet.next()){
            foodList.add(new Food(
                    Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("name")));
        }
        return foodList;

    }

    private Optional<Food> getFoodFromResultSet(ResultSet resultSet) throws SQLException{

        Food food = null;
        if (resultSet.next()) {
            food = new Food(
                    Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("name"));
        }
        return Optional.ofNullable(food);

    }

}
