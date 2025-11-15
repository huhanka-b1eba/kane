package com.kane.controller.user;

import com.kane.model.User;
import com.kane.service.AuthService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/profile/update")
public class ProfileUpdateServlet extends HttpServlet {
    private final AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || user.getId() == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            authService.updateProfile(user.getId(), username, email, password);
            // Обновляем сессию
            User updated = authService.findById(user.getId());
            session.setAttribute("user", updated);
            resp.sendRedirect(req.getContextPath() + "/profile");
        } catch (Exception e) {
            // Можно вернуть ошибку в шаблон, но для простоты — редирект
            resp.sendRedirect(req.getContextPath() + "/profile?error=update_failed");
        }
    }
}