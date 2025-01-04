package com.example.taskblock.service;

import org.springframework.stereotype.Service;

import com.example.taskblock.model.user.Member;
import com.example.taskblock.model.user.User;
import com.example.taskblock.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService  {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // Constructor-based injection
    public UserService(UserRepository userRepository,BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Register a new user.
     *
     * @param user The user object to be registered.
     * @return The saved User object.
     * @throws IllegalArgumentException if the email is already in use.
     */

     /* 
    public User registerUser(Member user) {
        // Check if the email already exists
        Optional<Member> existingUser = userRepository.findByEmail(user.getEmail());
        Optional<Member> existingHandle = userRepository.findByHandle(user.getHandle());

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email already exists!");
        }
        if (existingHandle.isPresent()) {
            throw new IllegalArgumentException("Handle already used!");
        }


        // Save the new user
        return userRepository.save(user);
    }
    */


    public User registerUser(Member user) {
        // Check if the email already exists
        Optional<Member> existingUser = userRepository.findByEmail(user.getEmail());
        Optional<Member> existingHandle = userRepository.findByHandle(user.getHandle());

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email already exists!");
        }
        if (existingHandle.isPresent()) {
            throw new IllegalArgumentException("Handle already used!");
        }


        // Encrypt the user's password
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);  // Set the encoded password to the user object

        // Save the new user
        return userRepository.save(user);
    }







    /**
     * Find a user by their ID.
     *
     * @param id The user's ID.
     * @return An Optional containing the user if found, or empty otherwise.
     */
    public Optional<Member> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Find a user by their email.
     *
     * @param email The user's email.
     * @return An Optional containing the user if found, or empty otherwise.
     */
    public Optional<Member> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Find a user by their Handle.
     *
     * @param handle The user's Handle.
     * @return An Optional containing the user if found, or empty otherwise.
     */
    public Optional<Member> findByHandle(String handle) {
        return userRepository.findByHandle(handle);
    }


    @Override
    public UserDetails loadUserByUsername(String handle) throws UsernameNotFoundException {
        // Fetch user from database
        Member user = userRepository.findByHandle(handle)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Convert User to UserDetails
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                null);
    }




}



