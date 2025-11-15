package com.kane.listener;

import com.kane.repository.transaction.CategoryRepository;
import com.kane.repository.transaction.CategoryRepositoryImpl;
import com.kane.repository.transaction.OperationRepository;
import com.kane.repository.transaction.OperationRepositoryImpl;
import com.kane.repository.user.UserRepository;
import com.kane.repository.user.UserRepositoryImpl;
import com.kane.service.AuthService;
import com.kane.service.CategoryService;
import com.kane.service.OperationService;
import com.kane.util.DBConnectionManager;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.HashMap;
import java.util.Map;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        DBConnectionManager.init();

        UserRepository userRepository = new UserRepositoryImpl();
        CategoryRepository categoryRepository = new CategoryRepositoryImpl();
        OperationRepository operationRepository = new OperationRepositoryImpl();

        AuthService authService = new AuthService();
        CategoryService categoryService = new CategoryService();
        OperationService operationService = new OperationService();

        Map<String, Object> services = new HashMap<>();

        services.put("userRepository", userRepository);
        services.put("categoryRepository", categoryRepository);
        services.put("operationRepository", operationRepository);

        services.put("authService", authService);
        services.put("categoryService", categoryService);
        services.put("operationService", operationService);

        context.setAttribute("services", services);

        System.out.println(">>>>> Application Services Initialized and Stored in ServletContext");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println(">>>>> Application Shutting Down, Cleaning Resources...");
    }
}
