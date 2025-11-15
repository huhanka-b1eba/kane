package com.kane.repository.user;

import com.kane.model.User;
import com.kane.util.DBConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class UserRepositoryImpl implements UserRepository {

    private static final Logger logger = LogManager.getLogger(UserRepositoryImpl.class);

    @Override
    public User findByEmail(String email) {
        logger.debug("Looking up user by email: {}", email);
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword_hash(resultSet.getString("password_hash"));
                user.setCreated_at(resultSet.getTimestamp("created_at"));
                logger.debug("User found: {}", email);
                return user;
            }
        } catch (SQLException e) {
            logger.error("Error finding user by email {}", email, e);
            throw new RuntimeException(e);
        }
        logger.debug("User not found: {}", email);
        return null;
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (username, email, password_hash, created_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword_hash());
            statement.setTimestamp(4, user.getCreated_at());

            int rows = statement.executeUpdate();
            logger.info("Saved user {} ({}), rows affected: {}", user.getUsername(), user.getEmail(), rows);
        } catch (SQLException e) {
            logger.error("Error saving user {} ({})", user.getUsername(), user.getEmail(), e);
        }
    }


    @Override
    public void updateProfile(long userId, String username, String email, String passwordHash) {
        String sql;
        if (passwordHash == null) {
            sql = "UPDATE users SET username = ?, email = ? WHERE id = ?";
        } else {
            sql = "UPDATE users SET username = ?, email = ?, password_hash = ? WHERE id = ?";
        }

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, email);
            if (passwordHash != null) {
                stmt.setString(3, passwordHash);
                stmt.setLong(4, userId);
            } else {
                stmt.setLong(3, userId);
            }

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("Пользователь с ID " + userId + " не найден");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка обновления профиля", e);
        }
    }

    @Override
    public User findById(long id) {
        String sql = "SELECT id, username, email, password_hash FROM users WHERE id = ?";
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getLong("id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                // пароль не возвращаем обычно, но можно
                return u;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения пользователя по ID", e);
        }
        return null;
    }
}
