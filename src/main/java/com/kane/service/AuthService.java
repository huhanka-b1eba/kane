package com.kane.service;

import com.kane.model.User;
import com.kane.repository.user.UserRepository;
import com.kane.repository.user.UserRepositoryImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthService {

    private static final Logger logger = LogManager.getLogger(AuthService.class);

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    UserRepository userRepository = new UserRepositoryImpl();

    public User login(String email, String password) {
        logger.debug("Login attempt for email: {}", email);

        User user = userRepository.findByEmail(email);

        if (user == null) {
            logger.warn("User with email {} not found", email);
            return null;
        }

        if (!encoder.matches(password, user.getPassword_hash())) {
            logger.warn("Password mismatch for user {}", email);
            return null;
        }

        logger.info("User {} authenticated successfully", email);
        return user;
    }

    public User register(String username, String email, String password) {
        logger.debug("Register attempt for email: {}", email);

        if (userRepository.findByEmail(email) != null) {
            logger.warn("Registration failed - email already exists: {}", email);
            return null;
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword_hash(encoder.encode(password));
        user.setCreated_at(new java.sql.Timestamp(System.currentTimeMillis()));

        userRepository.save(user);
        logger.info("User registered: {} ({})", username, email);
        return user;
    }

    public void updateProfile(long userId, String username, String email, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Некорректный email");
        }

        String passwordHash = null;
        if (password != null && !password.trim().isEmpty()) {
            passwordHash = hashPassword(password);
        }

        userRepository.updateProfile(userId, username, email, passwordHash);
    }

    private String hashPassword(String password) {
        try {
            return encoder.encode(password);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка хеширования пароля", e);
        }
    }

    public User findById(long id) {
        return userRepository.findById(id);
    }
}
