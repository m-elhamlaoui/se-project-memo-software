package com.example.taskblock.service;

import com.example.taskblock.model.wallet.Wallet;
import com.example.taskblock.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public Optional<Wallet> getWalletByUserAndTaskBlock(Long userId, Long taskBlockId) {
        return walletRepository.findByUserIdAndTaskBlockId(userId, taskBlockId);
    }

    public void addFundsToWallet(Wallet wallet, BigDecimal amount) {
        wallet.addFunds(amount);
        walletRepository.save(wallet);
    }

    public void deductFundsFromWallet(Wallet wallet, BigDecimal amount) {
        wallet.deductFunds(amount);
        walletRepository.save(wallet);
    }
}
