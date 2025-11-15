package com.kane.repository.transaction;

import com.kane.model.Category;

import java.util.List;

public interface CategoryRepository {
     void addCategory(Category category);

     List<Category> getAllCategories();

     void updateCategory(Category c);

     void deleteCategory(long id);
}
