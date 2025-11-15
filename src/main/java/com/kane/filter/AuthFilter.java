package com.kane.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Set;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final Set<String> PUBLIC_URLS = Set.of(
            "/login",
            "/register",
            "/error",
            "/static"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getServletPath();

        System.out.println(">>> FILTER PATH = " + path);

        // пропускаем статику
        if (path.startsWith("/static/")) {
            chain.doFilter(request, response);
            return;
        }

        boolean isPublic = PUBLIC_URLS.stream().anyMatch(path::startsWith);

        HttpSession session = req.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("user") != null;

        System.out.println("Session exists: " + (session != null));
        if (session != null) {
            System.out.println("User in session: " + session.getAttribute("user"));
        }

        if (!isPublic && !loggedIn) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }
}


