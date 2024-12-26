package org.example.erepairshop.entities;

public enum RepairOrderStatus {
    REPAIRING("text-primary-emphasis bg-primary-subtle"),
    FIXED("text-success-emphasis bg-success-subtle"),
    OBSOLETE("text-danger-emphasis bg-danger-subtle"),
    ON_HOLD("text-warning-emphasis bg-warning-subtle");

        private final String displayText;

    RepairOrderStatus(String displayText) {
            this.displayText = displayText;
        }

        public String getDisplayText() {
            return displayText;
        }
}
