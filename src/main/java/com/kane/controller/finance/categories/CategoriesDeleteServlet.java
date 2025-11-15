package com.kane.controller.finance.categories;

import com.kane.service.CategoryService;
import com.kane.service.OperationService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/categories/delete")
public class CategoriesDeleteServlet extends HttpServlet {
    private final CategoryService service = new CategoryService();
    private final OperationService operationService = new OperationService(); // добавьте

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));

        // Проверяем, есть ли операции с этой категорией
        if (!operationService.getOperationsByCategoryId(id).isEmpty()) {
            // Перенаправляем с ошибкой
            resp.sendRedirect(req.getContextPath() + "/categories?error=category_in_use");
            return;
        }

        service.deleteCategory(id);
        resp.sendRedirect(req.getContextPath() + "/categories");
    }
}