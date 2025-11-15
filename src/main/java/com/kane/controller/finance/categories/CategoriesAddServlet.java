package com.kane.controller.finance.categories;

import com.kane.util.FreemarkerConfig;
import com.kane.model.Category;
import com.kane.service.CategoryService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/categories/add")
public class CategoriesAddServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(CategoriesAddServlet.class);

    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Получаем параметры формы
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String color = request.getParameter("color");
        String icon = request.getParameter("icon");

        Category category = new Category();
        category.setName(name);
        category.setType(type);
        category.setColor(color);
        category.setIcon(icon);

        try {
            categoryService.addCategory(category);
            logger.info("Category added successfully: {} [{}]", name, type);
            response.sendRedirect(request.getContextPath() + "/categories");
        } catch (Exception e) {
            logger.error("Failed to add category: {}", e.getMessage(), e);

            Map<String, Object> model = new HashMap<>();
            model.put("error", e.getMessage());

            // Можно передавать существующие категории, чтобы они отобразились в шаблоне
            model.put("incomeCategories", categoryService.getIncomeCategories());
            model.put("expenseCategories", categoryService.getExpenseCategories());

            try {
                Template template = FreemarkerConfig.getConfig().getTemplate("finance/categories.ftl");
                response.setContentType("text/html; charset=UTF-8");
                template.process(model, response.getWriter());
            } catch (TemplateException te) {
                logger.error("Error processing categories.ftl", te);
                throw new ServletException("Ошибка обработки шаблона", te);
            }
        }
    }
}
