package com.example.taskblock.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User implements UserDetails {
    private static final long serialVersionUID = 1L;

    @ManyToMany
    @JoinTable(
            name = "member_friends",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    @JsonIgnore
    private List<Member> friends = new ArrayList<>();


    @JsonProperty("friends")
    public List<Map<String, Object>> getFriendsJson() {
        return friends.stream()
                .map(inviter -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", inviter.getId());
                    map.put("handle", inviter.getHandle());
                    return map;
                })
                .collect(Collectors.toList());
    }




    @ManyToMany
    @JoinTable(
            name = "member_invitations",
            joinColumns = @JoinColumn(name = "invited_id"),
            inverseJoinColumns = @JoinColumn(name = "inviter_id")
    )
    @JsonIgnore
    private List<Member> inviters = new ArrayList<>();

    @JsonProperty("inviters")
    public List<Map<String, Object>> getInvitersJson() {
        return inviters.stream()
                .map(inviter -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", inviter.getId());
                    map.put("handle", inviter.getHandle());
                    return map;
                })
                .collect(Collectors.toList());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String handle;

    @Column(nullable = false)
    private String password;

    private String profileImage;

    public List<Member> getFriends() {
        return friends;
    }

    public void addFriend(Member friend) {
        this.friends.add(friend);
    }

    public List<Member> getInviters() {
        return inviters;
    }

    public void addInviter(Member inviter) {
        this.inviters.add(inviter);
    }

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
