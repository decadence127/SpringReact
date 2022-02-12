package com.example.computerstore.services.implementations;

import com.example.computerstore.exception.ExceptionHandler;
import com.example.computerstore.models.CartModel;
import com.example.computerstore.models.UserModel;
import com.example.computerstore.repositories.CartRepository;
import com.example.computerstore.repositories.UserRepository;
import com.example.computerstore.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;


@Service
@DependsOn("passwordEncoder")
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserModel findOneByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Collection<UserModel> findByRole(String role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    @Transactional
    public UserModel saveUser(UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            UserModel userData = userRepository.save(user);
            CartModel cartData = cartRepository.save(new CartModel(userData));
            userData.setCart(cartData);
            return userRepository.save(userData);

        } catch (Exception e) {
            throw new ExceptionHandler(500, e.getMessage());
        }
    }

    @Override
    @Transactional
    public UserModel updateUser(UserModel user) {
        try {
            UserModel userCandidate = userRepository.findByEmail(user.getEmail());
            userCandidate.setPassword(user.getPassword());
            userCandidate.setName(user.getName());
            userCandidate.setPhoneNumber(user.getPhoneNumber());
            userCandidate.setAddress(user.getAddress());
            userCandidate.setIsActive(user.getIsActive());
            userCandidate.setRole(user.getRole());
            return userRepository.save(userCandidate);
        } catch (Exception e) {
            throw new ExceptionHandler(500, e.getMessage());
        }
    }

}
