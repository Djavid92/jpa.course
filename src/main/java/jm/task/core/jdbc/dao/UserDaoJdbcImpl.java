package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(50), " +
                "lastName VARCHAR(50), " +
                "age TINYINT)";
        try  (Connection conn = Util.getConnection();
            Statement stmt = conn.createStatement()) {
            System.out.println("Соединение установлено" + conn);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка создания таблицы", e);
        }
    }
    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления таблицы", e);
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setByte(3, age);
            pstmt.executeUpdate();
            System.out.println("User с именем — " + name + " добавлен в БД");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка добавления пользователя", e);
        }
    }
    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления пользователя", e);
        }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("lastName"), rs.getByte("age"));
                user.setId(rs.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения пользователей", e);
        }
        return users;
    }
    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка очистки таблицы", e);
        }
    }
}
