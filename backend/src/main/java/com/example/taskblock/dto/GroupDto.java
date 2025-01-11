package com.example.taskblock.dto;

import java.util.List;

public class GroupDto {
    private String name;
    private List<String> members;


    
    public GroupDto(String name, List<String> members) {
        this.name = name;
        this.members = members;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getMembers() {
        return members;
    }
    public void setMembers(List<String> members) {
        this.members = members;
    }

    



    
}
