package com.kane.repository.transaction;

import com.kane.model.Category;
import com.kane.util.DBConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {

    private static final Logger logger = LogManager.getLogger(CategoryRepositoryImpl.class);

    @Override
    public void addCategory(Category category) {
        String sql = "INSERT INTO categories (name, type, color, icon) VALUES (?, ?, ?, ?)";
        logger.debug("SQL -> {}", sql);

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, category.getName());
            statement.setString(2, category.getType());
            statement.setString(3, category.getColor());
            statement.setString(4, category.getIcon());
            statement.executeUpdate();

            logger.info("Категория '{}' добавлена в БД", category.getName());
        } catch (SQLException e) {
            logger.error("Ошибка при добавлении категории в базу данных", e);
            throw new RuntimeException("Ошибка при добавлении категории", e);
        }
    }

    @Override
    public List<Category> getAllCategories() {
        String sql = "SELECT id, name, type, color, icon FROM categories";
        List<Category> categories = new ArrayList<>();
        logger.debug("SQL -> {}", sql);

        try (Connection conn = DBConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getLong("id"));
                c.setName(rs.getString("name"));
                c.setType(rs.getString("type"));
                c.setColor(rs.getString("color"));
                c.setIcon(rs.getString("icon"));
                categories.add(c);
            }

            logger.info("Из БД получено {} категорий", categories.size());
        } catch (SQLException e) {
            logger.error("Ошибка при получении категорий из базы", e);
            throw new RuntimeException("Ошибка при получении категорий", e);
        }

        return categories;
    }

    @Override
    public void updateCategory(Category c) {
        String sql = "UPDATE categories SET name = ?, type = ?, color = ?, icon = ? WHERE id = ?";
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getName());
            stmt.setString(2, c.getType());
            stmt.setString(3, c.getColor());
            stmt.setString(4, c.getIcon());
            stmt.setLong(5, c.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка обновления категории", e);
        }
    }

    @Override
    public void deleteCategory(long id) {
        String sql = "DELETE FROM categories WHERE id = ?";
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления категории", e);
        }
    }
}
