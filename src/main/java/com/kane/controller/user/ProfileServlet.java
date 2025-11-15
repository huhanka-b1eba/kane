package com.kane.controller.user;

import com.kane.util.FreemarkerConfig;
import com.kane.model.User;
import com.kane.service.AuthService;
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

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(ProfileServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> model = new HashMap<>();

        // Получаем текущего пользователя из сессии
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                model.put("user", user);
            }
        }

        try {
            Template template = FreemarkerConfig.getConfig().getTemplate("user/profile.ftl");
            response.setContentType("text/html; charset=UTF-8");
            model.put("contextPath", request.getContextPath());
            template.process(model, response.getWriter());
            logger.info("GET /profile - template rendered successfully");
        } catch (TemplateException e) {
            logger.error("Error processing profile.ftl", e);
            throw new ServletException("Ошибка обработки шаблона", e);
        }
    }
}
