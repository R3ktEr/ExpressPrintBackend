package com.iesfranciscodelosrios.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "_Order")
public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "pickupDate")
    private LocalDateTime pickupDate;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = User.class)
    private User user;  //Consulta personalizada sin usuario para el listado de los pedidos
    @Column(name = "isPayed")
    private boolean isPayed;
    @Column(name = "isPickedUp")
    private boolean isPickedUp;
    @Column(name = "finalPrice")
    private double finalPrice;
    
    @JoinTable(name = "Discount_Order", joinColumns = @JoinColumn(name="id_order",nullable = false), 
    		inverseJoinColumns = @JoinColumn(name="id_discount", nullable = false), uniqueConstraints = {
    				@UniqueConstraint(columnNames = {"id_order","id_discount"})
    		})
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Discount.class)
    private List<Discount> discounts;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Document.class)
    private List<Document> documents;

    public Order() {
        this.id = -1L;
    }

    public Order(LocalDateTime pickupDate, User user, boolean isPayed, boolean isPickedUp, double finalPrice, List<Discount> discounts) {
        this.id = -1L;
        this.pickupDate = pickupDate;
        this.user = user;
        this.isPayed = isPayed;
        this.isPickedUp = isPickedUp;
        this.finalPrice = finalPrice;
        this.discounts = discounts;
    }

    public Order(LocalDateTime pickupDate, User user, boolean isPayed, boolean isPickedUp, List<Discount> discounts) {
        this.id = -1L;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", pickupDate=" + pickupDate + ", user=" + user + ", isPayed=" + isPayed + ", isPickedUp=" + isPickedUp + ", finalPrice=" + finalPrice + ", discounts=" + discounts + '}';
    }

}
