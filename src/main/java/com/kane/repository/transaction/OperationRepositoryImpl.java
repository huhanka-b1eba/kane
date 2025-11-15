package com.kane.repository.transaction;

import com.kane.model.Operation;
import com.kane.repository.user.UserRepositoryImpl;
import com.kane.util.DBConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperationRepositoryImpl implements OperationRepository {

    private static final Logger logger = LogManager.getLogger(UserRepositoryImpl.class);

    @Override
    public void addOperation(Operation operation) {
        // существующий код без изменений
        String sql = "INSERT INTO transactions (user_id, category_id, amount, type, transaction_date, note) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, operation.getUserId());
            stmt.setInt(2, operation.getCategoryId());
            stmt.setDouble(3, operation.getAmount());
            stmt.setString(4, operation.getType());
            stmt.setTimestamp(5, operation.getCreated_at());
            stmt.setString(6, operation.getNote());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении транзакции", e);
        }
    }

    @Override
    public List<Operation> getAllByUserId(long userId) {
        return getAllByUserId(userId, 0); // 0 = без лимита
    }

    // НОВЫЙ МЕТОД с лимитом
    public List<Operation> getAllByUserId(long userId, int limit) {
        List<Operation> list = new ArrayList<>();
        String sql = "SELECT t.*, c.name AS category_name " +
                "FROM transactions t " +
                "JOIN categories c ON t.category_id = c.id " +
                "WHERE t.user_id = ? " +
                "ORDER BY t.transaction_date DESC";

        // Добавляем LIMIT если нужно
        if (limit > 0) {
            sql += " LIMIT ?";
        }

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            if (limit > 0) {
                stmt.setInt(2, limit);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Operation t = new Operation();
                t.setId(rs.getLong("id"));
                t.setUserId(rs.getLong("user_id"));
                t.setCategoryId(rs.getInt("category_id"));
                t.setAmount(rs.getDouble("amount"));
                t.setType(rs.getString("type"));
                t.setCreated_at(rs.getTimestamp("transaction_date"));
                t.setNote(rs.getString("note"));
                t.setCategoryName(rs.getString("category_name"));
                list.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении транзакций", e);
        }
        return list;
    }

    // остальные методы без изменений
    @Override
    public void updateOperation(Operation op) {
        String sql = """
        UPDATE transactions 
        SET category_id = ?, amount = ?, type = ?, note = ? 
        WHERE id = ? AND user_id = ?
        """;

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, op.getCategoryId());
            stmt.setDouble(2, op.getAmount());
            stmt.setString(3, op.getType());
            stmt.setString(4, op.getNote());
            stmt.setLong(5, op.getId());
            stmt.setLong(6, op.getUserId());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                logger.warn("No operation updated for ID {} and user {}", op.getId(), op.getUserId());
            } else {
                logger.info("Operation {} updated successfully", op.getId());
            }
        } catch (SQLException e) {
            logger.error("Error updating operation {}", op.getId(), e);
            throw new RuntimeException("Failed to update operation", e);
        }
    }

    @Override
    public void deleteOperation(long id) {
        String sql = "DELETE FROM transactions WHERE id=?";
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении транзакции", e);
        }
    }

    public List<Operation> getByCategoryId(long categoryId) {
        String sql = "SELECT * FROM transactions WHERE category_id = ?";
        List<Operation> operations = new ArrayList<>();

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, categoryId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Operation op = new Operation();
                op.setId(rs.getLong("id"));
                op.setUserId(rs.getLong("user_id"));
                op.setCategoryId(rs.getInt("category_id"));
                op.setAmount(rs.getDouble("amount"));
                op.setType(rs.getString("type"));
                op.setCreated_at(rs.getTimestamp("transaction_date"));
                op.setNote(rs.getString("note"));
                operations.add(op);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении операций по категории ID: " + categoryId, e);
        }

        return operations; // возвращаем пустой список, если нет записей
    }

    @Override
    public Operation getById(long id) {
        String sql = "SELECT t.*, c.name AS category_name " +
                "FROM transactions t " +
                "JOIN categories c ON t.category_id = c.id " +
                "WHERE t.id=?";
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Operation t = new Operation();
                t.setId(rs.getLong("id"));
                t.setUserId(rs.getLong("user_id"));
                t.setCategoryId(rs.getInt("category_id"));
                t.setAmount(rs.getDouble("amount"));
                t.setType(rs.getString("type"));
                t.setCreated_at(rs.getTimestamp("transaction_date"));
                t.setNote(rs.getString("note"));
                t.setCategoryName(rs.getString("category_name"));
                return t;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении транзакции", e);
        }
        return null;
    }

    @Override
    public Map<String, Double> getUserStats(long userId) {
        Map<String, Double> stats = new HashMap<>();
        String sql = "SELECT " +
                "SUM(CASE WHEN type = 'income' THEN amount ELSE 0 END) as total_income, " +
                "SUM(CASE WHEN type = 'expense' THEN amount ELSE 0 END) as total_expense " +
                "FROM transactions WHERE user_id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double income = rs.getDouble("total_income");
                double expense = rs.getDouble("total_expense");
                double balance = income - expense;
                double delta = income - expense; // дельта = баланс

                stats.put("income", income);
                stats.put("expense", expense);
                stats.put("balance", balance);
                stats.put("delta", delta);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении статистики", e);
        }

        return stats;
    }
}