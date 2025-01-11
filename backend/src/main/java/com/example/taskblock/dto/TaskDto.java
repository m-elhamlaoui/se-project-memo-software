package com.example.taskblock.dto;

import java.util.ArrayList;
import java.util.List;

public class TaskDto {
    private String title;
    private String description;
    private Integer durationSeconds;
    private List<Long> taggedGroupIds = new ArrayList<>();
    private List<String> taggedMemberNames = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public List<Long> getTaggedGroupIds() {
        return taggedGroupIds;
    }

    public void setTaggedGroupIds(List<Long> taggedGroupIds) {
        this.taggedGroupIds = taggedGroupIds;
    }

    public List<String> getTaggedMemberNames() {
        return taggedMemberNames;
    }

    public void setTaggedMemberNames(List<String> taggedMemberNames) {
        this.taggedMemberNames = taggedMemberNames;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}