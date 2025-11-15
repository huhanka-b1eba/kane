package com.kane.controller.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(LogoutServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            logger.info("User logged out: {}", session.getAttribute("user"));
            session.invalidate();
        } else {
            logger.info("Logout called but no active session found");
        }

        response.sendRedirect(request.getContextPath() + "/register");
    }
}
