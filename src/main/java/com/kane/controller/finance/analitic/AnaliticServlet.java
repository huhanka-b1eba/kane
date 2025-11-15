package com.kane.controller.finance.analitic;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/analitic")
public class AnaliticServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(AnaliticServlet.class);
    private final OperationService operationService = new OperationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user.getId() == null) {
            response.sendRedirect("login");
            return;
        }

        long userId = user.getId();

        // 1. Общая статистика (доходы, расходы, баланс)
        Map<String, Double> stats = operationService.getUserStats(userId);

        // 2. Все операции
        List<Operation> allOperations = operationService.getUserOperations(userId);

        // 3. Только расходы
        List<Operation> expenses = allOperations.stream()
                .filter(op -> "expense".equals(op.getType()))
                .toList();

        // 4. Подготавливаем список расходов с цветом категории
        List<Category> expenseCategories = operationService.getExpenseCategories();
        Map<Long, Category> categoryMap = expenseCategories.stream()
                .collect(Collectors.toMap(Category::getId, c -> c));

        List<Map<String, Object>> expenseSummary = expenses.stream()
                .collect(Collectors.groupingBy(
                        Operation::getCategoryId,
                        Collectors.summingDouble(Operation::getAmount)
                ))
                .entrySet().stream()
                .map(entry -> {
                    long categoryId = entry.getKey();
                    double total = entry.getValue();
                    Category cat = categoryMap.get(categoryId);
                    String name = (cat != null) ? cat.getName() : "Без категории";
                    String color = (cat != null && cat.getColor() != null && !cat.getColor().isEmpty())
                            ? cat.getColor()
                            : "#9CA3AF"; // fallback: gray-400

                    Map<String, Object> item = new HashMap<>();
                    item.put("name", name);
                    item.put("amount", total);
                    item.put("color", color);
                    return item;
                })
                .sorted((a, b) -> Double.compare((Double) b.get("amount"), (Double) a.get("amount")))
                .toList();

        // 5. Передаём данные в шаблон
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("stats", stats);
        model.put("totalOperations", allOperations.size());
        model.put("expenseSummary", expenseSummary); // ← используем только это
        model.put("contextPath", request.getContextPath());

        try {
            Template template = FreemarkerConfig.getConfig().getTemplate("finance/analitic.ftl");
            response.setContentType("text/html; charset=UTF-8");
            template.process(model, response.getWriter());
            logger.info("GET /analitic - rendered with real data");
        } catch (TemplateException e) {
            logger.error("Error processing analitic.ftl", e);
            throw new ServletException("Ошибка обработки шаблона", e);
        }
    }
}
