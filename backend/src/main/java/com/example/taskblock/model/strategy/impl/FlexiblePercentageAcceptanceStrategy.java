package com.example.taskblock.model.strategy.impl;

import com.example.taskblock.model.strategy.PercentageAcceptanceStrategy;

public class FlexiblePercentageAcceptanceStrategy implements PercentageAcceptanceStrategy {
    private final double thresholdPercentage;

    public FlexiblePercentageAcceptanceStrategy(double thresholdPercentage) {
        this.thresholdPercentage = thresholdPercentage;
    }

    @Override
    public boolean shouldAccept(double acceptancePercentage) {
        return acceptancePercentage >= thresholdPercentage;
    }
}
