package com.kane.controller.finance.categories;

import com.kane.model.Category;
import com.kane.service.CategoryService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/categories/update")
public class CategoriesUpdateServlet extends HttpServlet {
    private final CategoryService service = new CategoryService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        long id = Long.parseLong(req.getParameter("id")); // ← обязательно
        String name = req.getParameter("name");
        String type = req.getParameter("type");
        String color = req.getParameter("color");
        String icon = req.getParameter("icon");

        Category c = new Category();
        c.setId(id);          // ← обязательно
        c.setName(name);
        c.setType(type);
        c.setColor(color);
        c.setIcon(icon);

        service.updateCategory(c);
        resp.sendRedirect(req.getContextPath() + "/categories");
    }
}