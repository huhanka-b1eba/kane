package com.kane.repository.user;

import com.kane.model.User;

public interface UserRepository {
    User findByEmail(String email);

    void save(User user);

    void updateProfile(long userId, String username, String email, String passwordHash);

    User findById(long id);

}
