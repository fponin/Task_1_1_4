package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE users (ID Bigint NOT NULL AUTO_INCREMENT, NAME varchar (255), LASTNAME varchar (255), AGE int (3), PRIMARY KEY (ID))";

        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица создана");
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Таблица уже существует");
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы");
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE users";

        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица удалена");
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Таблицы не существует");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы");
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?) ";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            System.out.println("User c именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("Ошибка при сохранении User");
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE ID = ?";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Пользователь с таким ID не найден");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении USER");
        }

    }

    public List<User> getAllUsers() {
        List<User> listUser = new ArrayList<>();
        String sql = "SELECT ID, NAME, LASTNAME, AGE FROM users";
        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));

                listUser.add(user);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return listUser;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE users";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            System.out.println("ошибка во время очистки таблицы");
        }
    }
}
