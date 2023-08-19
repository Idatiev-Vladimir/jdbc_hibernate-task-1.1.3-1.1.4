package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String TABLE_NAME = "users";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = " CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                     "( id SERIAL  PRIMARY KEY NOT NULL," +
                     " name VARCHAR(128) NOT NULL," +
                     " last_name VARCHAR(128) NOT NULL," +
                     " age INT NOT NULL)";

        try (Connection connection = Util.openConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = " DROP TABLE IF EXISTS " + TABLE_NAME;

        try (Connection connection = Util.openConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO " + TABLE_NAME + " (name, last_name, age) " +
                     " VALUES (?, ?, ?)";

        try (Connection connection = Util.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM " + TABLE_NAME +
                     " WHERE id = ?";

        try (Connection connection = Util.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;

        try (Connection connection = Util.openConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                Byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE " + TABLE_NAME;
        try (Connection connection = Util.openConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
