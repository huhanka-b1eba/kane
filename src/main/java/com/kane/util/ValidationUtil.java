package com.kane.util;

public class ValidationUtil {

    public static String validateRegistration(String username, String email, String password, String checkPassword) {
        if (username == null || username.trim().isEmpty()) {
            return "Заполните имя";
        }
        if (email == null || !email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            return "Некорректный email";
        }
        if (password == null || password.length() < 6) {
            return "Пароль должен быть не менее 6 символов";
        }
        if (!password.equals(checkPassword)) {
            return "Пароли не совпадают";
        }
        return null;
    }
}

