package data.repository;

import data.model.Restaurant;
import data.model.Worker;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class WorkerRepositoryImpl implements WorkerRepository{

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Override
    public Optional<Worker> findById(Long id) {

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = connection.prepareStatement("select * from worker where id=?");
             ResultSet resultSet = statement.executeQuery()){

            statement.setLong(1, id);

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

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Worker> findAll() {
        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = connection.prepareStatement("select * from worker");
             ResultSet resultSet = statement.executeQuery()){

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

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteById(Long id) {

        try (Connection connection = DriverManager.getConnection(dbUrl);
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

        try (Connection connection = DriverManager.getConnection(dbUrl);
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

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = connection.prepareStatement("select * from worker where name=?");
             ResultSet resultSet = statement.executeQuery()){

            statement.setString(1, name);

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

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }

}
