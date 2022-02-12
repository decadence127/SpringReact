package com.example.computerstore.repositories;

import com.example.computerstore.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
@Repository
public interface UserRepository extends JpaRepository<UserModel, String > {
    UserModel findByEmail(String Email);
    Collection<UserModel> findAllByRole(String role);
}
