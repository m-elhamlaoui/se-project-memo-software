package com.example.taskblock.repository;

import com.example.taskblock.model.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Query("SELECT w FROM Wallet w WHERE w.user.id = :userId AND w.taskBlock.id = :taskBlockId")
    Optional<Wallet> findByUserIdAndTaskBlockId(@Param("userId") Long userId, @Param("taskBlockId") Long taskBlockId);

    List<Wallet> findByUserId(Long userId);

    // Optional: Get wallets with taskblock data in a single query
    @Query("SELECT w FROM Wallet w JOIN FETCH w.taskBlock WHERE w.user.id = :userId")
    List<Wallet> findByUserIdWithTaskBlocks(@Param("userId") Long userId);

}
