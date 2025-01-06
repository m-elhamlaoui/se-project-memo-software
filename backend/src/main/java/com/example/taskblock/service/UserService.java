package com.example.taskblock.service;

import com.example.taskblock.model.user.User;
import com.example.taskblock.model.user.Member;
import com.example.taskblock.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<Member> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public Optional<Member> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<Member> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<Member> findByHandle(String handle) {
        return userRepository.findByHandle(handle);
    }
}
