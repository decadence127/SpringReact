package com.example.computerstore.services;

import com.example.computerstore.models.UserModel;

import java.util.Collection;

public interface IUserService {
    UserModel findOneByEmail(String email);
    Collection<UserModel> findByRole(String role);
    UserModel saveUser(UserModel user);
    UserModel updateUser(UserModel user);
}
