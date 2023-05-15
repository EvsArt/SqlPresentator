package data.repository;

import data.model.Food;
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

    @Override
    public Optional<Food> findById(Integer id) {

        try (Connection connection = DriverManager.getConnection(dbUrl);
                PreparedStatement statement = connection.prepareStatement("select * from food where id=?");
                ResultSet resultSet = statement.executeQuery()){

            statement.setString(1, String.valueOf(id));

            Food food = null;
            if (resultSet.next()) {
                food = new Food(
                        Integer.parseInt(resultSet.getString("id")),
                        resultSet.getString("name"));
            }
            return Optional.ofNullable(food);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Food> findAll() {
        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = connection.prepareStatement("select * from food");
             ResultSet resultSet = statement.executeQuery()){

            List<Food> foodList = new ArrayList<>();
            while (resultSet.next()){
                foodList.add(new Food(
                        Integer.parseInt(resultSet.getString("id")),
                        resultSet.getString("name")));
            }
            return foodList;

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteById(Integer id) {

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = connection.prepareStatement("delete from food where id=?"))
             {

            statement.setString(1, String.valueOf(id));
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Food> save(Food food) {

            try (Connection connection = DriverManager.getConnection(dbUrl);
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO food VALUES(name=?)"))
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

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = connection.prepareStatement("select * from food where name=?");
             ResultSet resultSet = statement.executeQuery()){

            statement.setString(1, name);

            Food food = null;
            if (resultSet.next()) {
                food = new Food(
                        Integer.parseInt(resultSet.getString("id")),
                        resultSet.getString("name"));
            }
            return Optional.ofNullable(food);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }
}
