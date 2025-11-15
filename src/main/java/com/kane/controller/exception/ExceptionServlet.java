package com.kane.controller.exception;

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

@WebServlet("/exception")
public class ExceptionServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(ExceptionServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.info("GET /exception - rendering exception page");

        Map<String, Object> model = new HashMap<>();
        model.put("title", "Ошибка — Kane");

        // Можно передавать сообщение об ошибке, если оно есть
        String errorMessage = (String) request.getAttribute("error");
        if (errorMessage != null) {
            model.put("error", errorMessage);
        }

        try {
            Template template = FreemarkerConfig.getConfig().getTemplate("exception.ftl");
            response.setContentType("text/html; charset=UTF-8");
            template.process(model, response.getWriter());
        } catch (TemplateException e) {
            logger.error("Ошибка при рендеринге exception.ftl", e);
            throw new ServletException("Ошибка обработки шаблона", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        logger.info("POST /exception - redirecting to profile page");

        Map<String, Object> model = new HashMap<>();
        model.put("title", "Профиль — Kane");

        try {
            Template template = FreemarkerConfig.getConfig().getTemplate("profile.ftl");
            response.setContentType("text/html; charset=UTF-8");
            template.process(model, response.getWriter());
        } catch (Exception e) {
            logger.error("Ошибка при рендеринге profile.ftl", e);
            throw new ServletException("Ошибка обработки шаблона", e);
        }
    }
}
