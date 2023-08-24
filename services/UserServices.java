package com.example.demo1.services;

import com.example.demo1.model.User;

public interface UserServices {
    public User createUser(User user);
    public User getUserById(Long id);
    public User updateUser(Long id, User updatedUser) throws Exception;
    public void deleteUser(Long id);

    public String generateOtp(String email);

}
