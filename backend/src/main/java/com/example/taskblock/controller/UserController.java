package com.example.taskblock.controller;

import com.example.taskblock.model.user.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.taskblock.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    // OK
    /* 
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Member user) {
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(registeredUser);
        } catch (IllegalArgumentException e) {
           return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    */
    // OK
    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<Member> getUserById(@PathVariable Long id) {
        Optional<Member> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    // OK
    // Get user by email
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/email/{email}")
    public ResponseEntity<Member> getUserByEmail(@PathVariable String email) {
        Optional<Member> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    // OK
      // Get user by handle
      @GetMapping("/handle/{handle}")
      public ResponseEntity<Member> getUserByHandle(@PathVariable String handle) {
          Optional<Member> user = userService.findByHandle(handle);
          return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
      }
}
