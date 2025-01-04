package com.example.taskblock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskblock.model.user.Member;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Member, Long> {

    // Find a user by email
    Optional<Member> findByEmail(String email);
    // Find a user by username (optional for additional functionality)
    Optional<Member> findByHandle(String handle);
    Optional<Member> findById(Long id);

}
