package com.iesfranciscodelosrios.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private LocalDateTime pickupDate;
    private User user;
    private boolean isPayed;
    private boolean isPickedUp;
    private double finalPrice;
    private List<Discount> discounts;

    public Order(){
        this.id = -1L;
    }

    public Order(Long id, LocalDateTime pickupDate, User user, boolean isPayed, boolean isPickedUp, double finalPrice, List<Discount> discounts) {
        this.id = id;
        this.pickupDate = pickupDate;
        this.user = user;
        this.isPayed = isPayed;
        this.isPickedUp = isPickedUp;
        this.finalPrice = finalPrice;
        this.discounts = discounts;
    }

    public Order(Long id, LocalDateTime pickupDate, User user, boolean isPayed, boolean isPickedUp, List<Discount> discounts) {
        this.id = id;
        this.pickupDate = pickupDate;
        this.user = user;
        this.isPayed = isPayed;
        this.isPickedUp = isPickedUp;
        this.discounts = discounts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDateTime pickupDate) {
        this.pickupDate = pickupDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public boolean isPickedUp() {
        return isPickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        isPickedUp = pickedUp;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

}
