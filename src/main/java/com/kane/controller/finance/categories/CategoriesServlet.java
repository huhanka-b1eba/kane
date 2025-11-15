package com.kane.controller.finance.categories;

import com.kane.model.Category;
import com.kane.service.CategoryService;
import com.kane.util.FreemarkerConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@WebServlet("/categories")
public class CategoriesServlet extends HttpServlet {
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        List<Category> income = categoryService.getIncomeCategories();
        List<Category> expense = categoryService.getExpenseCategories();

        Map<String, Object> data = new HashMap<>();
        data.put("incomeCategories", income);
        data.put("expenseCategories", expense);
        data.put("user", req.getSession().getAttribute("user"));
        data.put("contextPath", req.getContextPath());
        data.put("Request", req);

        // Получаем шаблон
        Template template = FreemarkerConfig.getConfig().getTemplate("finance/categories.ftl");

        resp.setContentType("text/html; charset=UTF-8");
        try (Writer out = resp.getWriter()) {
            template.process(data, out);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}

