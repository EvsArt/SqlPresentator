package data.repository;

import data.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository{

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Override
    public Optional<Order> findById(Long id) {

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = connection.prepareStatement("select * from \"order\" where id=?");
             ResultSet resultSet = statement.executeQuery()){

            statement.setLong(1, id);

            Order order = null;
            if (resultSet.next()) {
                order = new Order(
                        Long.parseLong(resultSet.getString("id")),
                        Integer.parseInt(resultSet.getString("type")),
                        resultSet.getString("comment"),
                        resultSet.getDate("order_time"),
                        resultSet.getBoolean("is_finished")
                );
            }
            return Optional.ofNullable(order);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Order> findAll() {
        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = connection.prepareStatement("select * from \"order\"");
             ResultSet resultSet = statement.executeQuery()){

            List<Order> orderList = new ArrayList<>();
            while (resultSet.next()){
                orderList.add(new Order(
                        Long.parseLong(resultSet.getString("id")),
                        Integer.parseInt(resultSet.getString("type")),
                        resultSet.getString("comment"),
                        resultSet.getDate("order_time"),
                        resultSet.getBoolean("is_finished"))
                );
            }
            return orderList;

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteById(Long id) {

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = connection.prepareStatement("delete from \"order\" where id=?"))
        {

            statement.setString(1, String.valueOf(id));
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Order> save(Order order) {

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO \"order\" VALUES(type=?, comment=?, order_time=?, is_finished=?)"))
        {
            statement.setInt(1, order.getType());
            statement.setString(2, order.getComment());
            statement.setLong(3, order.getOrderTime().getTime());
            statement.setBoolean(4, order.getIsFinished());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return findByOrderTime(order.getOrderTime());

    }

    private Optional<Order> findByOrderTime(java.util.Date orderTime){

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = connection.prepareStatement(
                     "select * from \"order\" where order_time=?"
             );
             ResultSet resultSet = statement.executeQuery()){

            statement.setLong(1, orderTime.getTime());

            Order order = null;
            if (resultSet.next()) {
                order = new Order(
                        Long.parseLong(resultSet.getString("id")),
                        Integer.parseInt(resultSet.getString("type")),
                        resultSet.getString("comment"),
                        resultSet.getDate("order_time"),
                        resultSet.getBoolean("is_finished")
                );
            }
            return Optional.ofNullable(order);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }

}
