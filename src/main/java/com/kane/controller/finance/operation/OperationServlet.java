package com.kane.controller.finance.operation;

import com.kane.model.Category;
import com.kane.model.Operation;
import com.kane.model.User;
import com.kane.service.OperationService;
import com.kane.util.FreemarkerConfig;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/operation")
public class OperationServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(OperationServlet.class);
    private final OperationService operationService = new OperationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");

        // ✅ ДОБАВИЛ ПРОВЕРКУ НА NULL ID
        if (user == null || user.getId() == null) {
            logger.error("User object is null or user ID is null in session");
            response.sendRedirect("login");
            return;
        }

        long userId = user.getId();

        // Получаем операции пользователя
        List<Operation> operations = operationService.getUserOperations(userId);

        // Получаем категории
        List<Category> incomeCategories = operationService.getIncomeCategories();
        List<Category> expenseCategories = operationService.getExpenseCategories();

        // Добавляем названия категорий для каждой транзакции
        for (Operation t : operations) {
            for (Category cat : incomeCategories) {
                if (cat.getId() == t.getCategoryId()) {
                    t.setCategoryName(cat.getName());
                    break;
                }
            }
            for (Category cat : expenseCategories) {
                if (cat.getId() == t.getCategoryId()) {
                    t.setCategoryName(cat.getName());
                    break;
                }
            }
        }

        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("operations", operations);
        model.put("incomeCategories", incomeCategories);
        model.put("expenseCategories", expenseCategories);
        model.put("contextPath", request.getContextPath());

        try {
            Template template = FreemarkerConfig.getConfig().getTemplate("finance/operation.ftl");
            response.setContentType("text/html; charset=UTF-8");
            template.process(model, response.getWriter());
        } catch (TemplateException e) {
            logger.error("Ошибка шаблона", e);
            throw new ServletException("Ошибка шаблона", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");

        if (user == null || user.getId() == null) {
            logger.error("User object is null or user ID is null in session");
            response.sendRedirect("login");
            return;
        }

        long userId = user.getId();

        String action = request.getParameter("action");

        switch (action) {
            case "delete" -> {
                long id = Long.parseLong(request.getParameter("id"));
                operationService.deleteOperation(id);
            }
            case "update" -> {
                Operation t = new Operation();
                t.setId(Long.parseLong(request.getParameter("id")));
                t.setUserId(userId);
                t.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
                t.setAmount(Double.parseDouble(request.getParameter("amount")));
                t.setType(request.getParameter("type"));
                t.setNote(request.getParameter("note"));
                operationService.updateOperation(t);
            }
            default -> { // add
                Operation operation = new Operation();
                operation.setUserId(userId);
                operation.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
                operation.setAmount(Double.parseDouble(request.getParameter("amount")));
                operation.setType(request.getParameter("type"));
                operation.setNote(request.getParameter("note"));
                operation.setCreated_at(new Timestamp(System.currentTimeMillis()));
                operationService.addOperation(operation);
            }
        }

        response.sendRedirect("operation");
    }
}