package com.example.taskblock.model.strategy.impl;

import com.example.taskblock.model.strategy.TimeRejectionStrategy;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class FixedTimeRejectionStrategy implements TimeRejectionStrategy {
    private final long durationInMinutes;

    public FixedTimeRejectionStrategy(long durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    @Override
    public boolean shouldReject(LocalDateTime createdAt) {
        long elapsedMinutes = ChronoUnit.MINUTES.between(createdAt, LocalDateTime.now());
        System.out.println("Elapsed Minutes: " + elapsedMinutes);
        return elapsedMinutes > durationInMinutes;
    }


}
