package com.example.taskblock.model.strategy;

import java.time.LocalDateTime;

public interface TimeRejectionStrategy {
    boolean shouldReject(LocalDateTime createdAt);
}
