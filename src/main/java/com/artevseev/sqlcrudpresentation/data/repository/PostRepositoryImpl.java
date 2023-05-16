package com.artevseev.sqlcrudpresentation.data.repository;

import com.artevseev.sqlcrudpresentation.data.model.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository{

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public Optional<Post> findById(Integer id) {

        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from post where id=?")){

            statement.setString(1, String.valueOf(id));
            resultSet = statement.executeQuery();

            return getPostFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Post> findAll() {

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from post");
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
             PreparedStatement statement = connection.prepareStatement("delete from post where id=?"))
        {

            statement.setString(1, String.valueOf(id));
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Post> save(Post post) {

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO post VALUES(name=?)"))
        {

            statement.setString(1, post.getName());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return findByName(post.getName());

    }

    @Override
    public Optional<Post> findByName(String name) {

        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from post where name=?")){

            statement.setString(1, name);
            resultSet = statement.executeQuery();

            return getPostFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }

    @Override
    public List<String> findAllPostNames() {
        return null;
    }

    private List<Post> getListFromResultSet(ResultSet resultSet) throws SQLException{

        List<Post> postList = new ArrayList<>();
        while (resultSet.next()){
            postList.add(new Post(
                    Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("name")));
        }
        return postList;

    }

    private Optional<Post> getPostFromResultSet(ResultSet resultSet) throws SQLException{

        Post post = null;
        if (resultSet.next()) {
            post = new Post(
                    Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("name"));
        }
        return Optional.ofNullable(post);

    }

}
