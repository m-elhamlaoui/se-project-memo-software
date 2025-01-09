package com.example.taskblock.model.taskblock;

import com.example.taskblock.model.user.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "taskblock_groups")
public class TaskBlockGroup {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskblock_id", nullable = false)
    @JsonIgnore
    private TaskBlock taskBlock;

    @JsonProperty("taskBlock")
    public Long gettaskBlockJson() {
        return taskBlock.getId();
    }

    @ManyToMany
    @JoinTable(
            name = "taskblock_group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    @OrderColumn(name = "member_order")  // This maintains the list order
    @JsonIgnore
    private List<Member> members = new ArrayList<>();

    @JsonProperty("members")
    public List<Map<String, Object>> getMembersJson() {
        return members.stream()
                .map(inviter -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", inviter.getId());
                    map.put("handle", inviter.getHandle());
                    return map;
                })
                .collect(Collectors.toList());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskBlock getTaskBlock() {
        return taskBlock;
    }

    public void setTaskBlock(TaskBlock taskBlock) {
        this.taskBlock = taskBlock;
    }
// Getters and setters
}