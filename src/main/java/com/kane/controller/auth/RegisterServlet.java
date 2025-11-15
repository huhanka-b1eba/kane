package com.kane.controller.auth;

import com.kane.model.User;
import com.kane.service.AuthService;
import com.kane.util.ValidationUtil;
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
import java.util.Map;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(RegisterServlet.class);
    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.info("GET /register - showing registration page");

        // Подготовка данных для шаблона
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Регистрация — Kane");
        model.put("contextPath", request.getContextPath());

        // Получаем шаблон и обрабатываем
        try {
            Template template = FreemarkerConfig.getConfig().getTemplate("auth/registration.ftl");
            response.setContentType("text/html; charset=UTF-8");
            template.process(model, response.getWriter());
        } catch (TemplateException e) {
            logger.error("Ошибка обработки шаблона регистрации", e);
            throw new ServletException("Template processing error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.info("POST /register - registration attempt");

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String checkpassword = request.getParameter("checkpassword");

        String validation = ValidationUtil.validateRegistration(username, email, password, checkpassword);

        Map<String, Object> model = new HashMap<>();
        model.put("title", "Регистрация — Kane");
        model.put("username", username);
        model.put("email", email);

        if (validation != null) {
            logger.warn("Registration validation failed: {}", validation);
            model.put("error", validation);
            renderTemplate(response, model);
            return;
        }

        User user = authService.register(username, email, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            logger.info("New user registered: {}", email);
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            logger.warn("Registration failed - user with email {} already exists", email);
            model.put("error", "Пользователь с таким email уже существует");
            renderTemplate(response, model);
        }
    }

    private void renderTemplate(HttpServletResponse response, Map<String, Object> model)
            throws IOException, ServletException {
        try {
            Template template = FreemarkerConfig.getConfig().getTemplate("auth/registration.ftl");
            response.setContentType("text/html; charset=UTF-8");
            template.process(model, response.getWriter());
        } catch (TemplateException e) {
            throw new ServletException("Template processing error", e);
        }
    }
}
