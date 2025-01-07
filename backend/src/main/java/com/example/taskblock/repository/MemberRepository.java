package com.example.taskblock.repository;

import com.example.taskblock.model.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // Find a Member by email
    Optional<Member> findByEmail(String email);
    // Find a Member by Membername (optional for additional functionality)
    Optional<Member> findByHandle(String handle);
    Optional<Member> findById(Long id);

}
