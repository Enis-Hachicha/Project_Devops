package org.example.erepairshop.entities;

public enum PaymentStatus {
    PENDING("Pending"),
    CANCELED("Canceled"),
    PAID("Paid");

    private final String displayText;

    PaymentStatus(String displayText) {
        this.displayText = displayText;
    }

    public String getDisplayText() {
        return displayText;
    }
}
