package com.example.web.service;


import com.example.web.models.User;
import com.example.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public Optional<User> getUserById(Long Id) {
        Optional<User> user = userRepository.findById(Id);
        return user;
    }
    public Optional<User> findUserbyUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return user;
    }

}
