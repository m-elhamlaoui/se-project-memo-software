package com.example.taskblock.dto;

import java.util.List;

public class TaskBlockDto {
    private String name;
    private double percentageToAccept;
    private long voteDurationInMinutes;
    private List<String> members;
    private List<GroupDto> groups;

    public TaskBlockDto(String name, double percentageToAccept, long voteDurationInMinutes, List<String> members,
            List<GroupDto> groups) {
        this.name = name;
        this.percentageToAccept = percentageToAccept;
        this.voteDurationInMinutes = voteDurationInMinutes;
        this.members = members;
        this.groups = groups;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPercentageToAccept() {
        return percentageToAccept;
    }
    public void setPercentageToAccept(double percentageToAccept) {
        this.percentageToAccept = percentageToAccept;
    }
    public long getVoteDurationInMinutes() {
        return voteDurationInMinutes;
    }
    public void setVoteDurationInMinutes(long voteDurationInMinutes) {
        this.voteDurationInMinutes = voteDurationInMinutes;
    }
    public List<GroupDto> getGroups() {
        return groups;
    }
    public void setGroups(List<GroupDto> groups) {
        this.groups = groups;
    }
    public List<String> getMembers() {
        return members;
    }
    public void setMembers(List<String> members) {
        this.members = members;
    }

    
}
