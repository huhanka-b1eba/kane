package com.kane.controller.dashboard;

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
import java.util.*;

@WebServlet(urlPatterns = {"/", "/home"})
public class HomeServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(HomeServlet.class);
    private final OperationService operationService = new OperationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.info("GET /home - rendering dashboard");

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        Map<String, Object> model = new HashMap<>();
        model.put("title", "Главная — Kane");
        model.put("user", user);
        model.put("contextPath", request.getContextPath());

        if (user != null && user.getId() != null) {
            long userId = user.getId();

            // Получаем последние операции
            List<Operation> operations = operationService.getUserOperations(userId, 5);
            model.put("operations", operations);

            // Получаем статистику
            Map<String, Double> stats = operationService.getUserStats(userId);
            model.put("stats", stats);

            logger.info("Stats for user {}: income={}, expense={}, balance={}",
                    userId, stats.get("income"), stats.get("expense"), stats.get("balance"));

        } else {
            model.put("operations", List.of());
            model.put("stats", Map.of(
                    "income", 0.0,
                    "expense", 0.0,
                    "balance", 0.0,
                    "delta", 0.0
            ));

            if (user != null && user.getId() == null) {
                logger.warn("User object exists but ID is null: {}", user.getEmail());
            }
        }

        try {
            Template template = FreemarkerConfig.getConfig().getTemplate("dashboard/dashboard.ftl");
            response.setContentType("text/html; charset=UTF-8");
            template.process(model, response.getWriter());
        } catch (TemplateException e) {
            logger.error("Ошибка при рендеринге dashboard.ftl", e);
            throw new ServletException("Ошибка обработки шаблона", e);
        }
    }
}