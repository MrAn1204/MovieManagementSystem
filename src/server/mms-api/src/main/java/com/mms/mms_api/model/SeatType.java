package com.mms.mms_api.model;

public enum SeatType {
    STANDARD(1.0),
    PREMIUM(1.5),
    COUPLE(1.75),
    ACCESSIBLE(0.5);

    private final double multiplier;

    SeatType(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
