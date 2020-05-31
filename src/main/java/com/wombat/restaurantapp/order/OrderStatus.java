package com.wombat.restaurantapp.order;


public enum OrderStatus {
    PENDING,
    IN_KITCHEN,
    COMPLETED,
    CANCELLED;

    public String getDisplayValue() {
        switch(this) {
            case PENDING: return "Pending";
            case IN_KITCHEN: return "In Kitchen";
            case COMPLETED: return "Completed";
            case CANCELLED: return "Cancelled";
        }
        return "INVALID";
    }
}
