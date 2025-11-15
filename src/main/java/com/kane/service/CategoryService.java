package com.kane.service;

import com.kane.model.Category;
import com.kane.repository.transaction.CategoryRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryService {

    private static final Logger logger = LogManager.getLogger(CategoryService.class);
    private final CategoryRepositoryImpl repository = new CategoryRepositoryImpl();

    public void addCategory(Category category) {
        logger.debug("Попытка добавить категорию: {}", category);

        if (category.getName() == null || category.getName().isEmpty() ||
                category.getType() == null || category.getType().isEmpty()) {
            logger.warn("Ошибка валидации категории: отсутствует имя или тип");
            throw new IllegalArgumentException("Название и тип категории обязательны");
        }

        if (category.getColor() == null || category.getColor().isEmpty()) category.setColor("#FFFFFF");
        if (category.getIcon() == null || category.getIcon().isEmpty()) category.setIcon("ri-folder-2-fill");

        try {
            repository.addCategory(category);
            logger.info("Категория '{}' успешно сохранена", category.getName());
        } catch (Exception e) {
            logger.error("Ошибка при сохранении категории", e);
            throw e;
        }
    }

    public List<Category> getIncomeCategories() {
        logger.debug("Получение категорий с типом income");
        return repository.getAllCategories().stream()
                .filter(c -> "income".equals(c.getType()))
                .collect(Collectors.toList());
    }

    public List<Category> getExpenseCategories() {
        logger.debug("Получение категорий с типом expense");
        return repository.getAllCategories().stream()
                .filter(c -> "expense".equals(c.getType()))
                .collect(Collectors.toList());
    }

    public void updateCategory(Category category) {
        if (category.getId() == null || category.getId() <= 0) {
            throw new IllegalArgumentException("ID категории обязателен для обновления");
        }
        repository.updateCategory(category);
    }

    public void deleteCategory(long id) {
        repository.deleteCategory(id);
    }
}
