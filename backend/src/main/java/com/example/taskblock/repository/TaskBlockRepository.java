package com.example.taskblock.repository;

import com.example.taskblock.model.taskblock.TaskBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskBlockRepository extends JpaRepository<TaskBlock, Long> {
    @Query("SELECT tb FROM TaskBlock tb WHERE tb.id = :taskBlockId")
    Optional<TaskBlock> findById(@Param("taskBlockId") Long taskBlockId);

    // Existing method for user's taskblocks
    @Query("SELECT DISTINCT tb FROM TaskBlock tb " +
            "LEFT JOIN tb.wallets w " +
            "WHERE tb.creator.id = :userId OR w.user.id = :userId")
    List<TaskBlock> findTaskBlocksByUserIdOrWalletUserId(@Param("userId") Long userId);
}
