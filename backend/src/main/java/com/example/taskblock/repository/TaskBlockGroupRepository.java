package com.example.taskblock.repository;

import com.example.taskblock.model.taskblock.TaskBlockGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskBlockGroupRepository extends JpaRepository<TaskBlockGroup, Long> {
    // Find all groups in a taskblock
    List<TaskBlockGroup> findByTaskBlockId(Long taskBlockId);

    // Find a specific group by name in a taskblock
    Optional<TaskBlockGroup> findByTaskBlockIdAndName(Long taskBlockId, String name);

    // Check if a group name exists in a taskblock
    boolean existsByTaskBlockIdAndName(Long taskBlockId, String name);

    // Find all groups containing a specific member
    @Query("SELECT g FROM TaskBlockGroup g JOIN g.members m WHERE m.id = :memberId")
    List<TaskBlockGroup> findAllGroupsForMember(Long memberId);

    // Find groups by multiple IDs within a specific taskblock
    @Query("SELECT g FROM TaskBlockGroup g WHERE g.taskBlock.id = :taskBlockId AND g.id IN :groupIds")
    List<TaskBlockGroup> findByTaskBlockIdAndGroupIds(Long taskBlockId, List<Long> groupIds);

    // Delete all groups in a taskblock
    void deleteByTaskBlockId(Long taskBlockId);
}