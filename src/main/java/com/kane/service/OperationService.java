package com.kane.service;

import com.kane.model.Operation;
import com.kane.model.Category;
import com.kane.repository.transaction.CategoryRepository;
import com.kane.repository.transaction.CategoryRepositoryImpl;
import com.kane.repository.transaction.OperationRepository;
import com.kane.repository.transaction.OperationRepositoryImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OperationService {

    private final OperationRepository operationRepository = new OperationRepositoryImpl();
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();

    public void addOperation(Operation Operation) {
        operationRepository.addOperation(Operation);
    }

    // Существующий метод (все операции)
    public List<Operation> getUserOperations(long userId) {
        return operationRepository.getAllByUserId(userId);
    }

    public List<Operation> getUserOperations(long userId, int limit) {
        return operationRepository.getAllByUserId(userId, limit);
    }

    public void updateOperation(Operation Operation) {
        operationRepository.updateOperation(Operation);
    }

    public void deleteOperation(long id) {
        operationRepository.deleteOperation(id);
    }

    public List<Category> getIncomeCategories() {
        return categoryRepository.getAllCategories().stream()
                .filter(c -> "income".equals(c.getType()))
                .collect(Collectors.toList());
    }

    public List<Category> getExpenseCategories() {
        return categoryRepository.getAllCategories().stream()
                .filter(c -> "expense".equals(c.getType()))
                .collect(Collectors.toList());
    }

    public Map<String, Double> getUserStats(long userId) {
        return operationRepository.getUserStats(userId);
    }

    public List<Operation> getOperationsByCategoryId(long categoryId) {
        return operationRepository.getByCategoryId(categoryId);
    }
}