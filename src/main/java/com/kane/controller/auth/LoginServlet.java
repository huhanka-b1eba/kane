package com.kane.controller.auth;

import com.kane.model.User;
import com.kane.service.AuthService;
import com.kane.util.FreemarkerConfig;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(LoginServlet.class);
    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("GET /login - showing login page");

        resp.setContentType("text/html; charset=UTF-8");

        Map<String, Object> data = new HashMap<>();
        data.put("error", req.getAttribute("error"));

        Template template = FreemarkerConfig.getConfig().getTemplate("auth/login.ftl");
        data.put("contextPath", req.getContextPath());
        try (Writer out = resp.getWriter()) {
            template.process(data, out);
        } catch (TemplateException e) {
            throw new RuntimeException("Ошибка при рендеринге шаблона login.ftl", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("POST /login - login attempt");

        req.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = authService.login(email, password);

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            logger.info("User logged in successfully: {}", email);
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            logger.warn("Failed login attempt for email: {}", email);
            req.setAttribute("error", "Неверный email или пароль");
            doGet(req, resp); // 🔹 повторно вызываем doGet, чтобы снова отрендерить шаблон с ошибкой
        }
    }
}
