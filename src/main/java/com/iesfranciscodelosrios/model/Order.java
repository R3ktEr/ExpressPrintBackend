package com.iesfranciscodelosrios.model;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

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
    @ApiModelProperty(value = "Id del pedido",name="id", dataType = "Long", required=false,example="1")
    @Column(name = "id")
    private Long id;
    @ApiModelProperty(value = "Fecha de recogida",name="pickupDate",dataType = "LocalDateTime",required=false,example="2022-06-19T19:23:03")
    @Column(name = "pickup_Date")
    private LocalDateTime pickupDate;
    @ApiModelProperty(value = "Fecha del pedido",name="orderDate",dataType = "LocalDateTime",required=false,example="2022-06-19T19:23:03")
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    @JsonIgnoreProperties("userOrders")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER, targetEntity = User.class)
    @ApiModelProperty(value = "Id del usuario",name="user",dataType = "User" ,required=true)
    @JoinColumn(name="id_user", nullable = false)
    private User user;
    @ApiModelProperty(value = "Indica si el pedido esta pagado",name="isPayed",dataType = "boolean",required=false,example="false")
    @Column(name = "is_Payed")
    private boolean isPayed;
    @ApiModelProperty(value = "Indica si el pedido se ha recogido",name="isPickedUp",dataType = "boolean",required=false,example="false")
    @Column(name = "is_Picked_Up")
    private boolean isPickedUp;
    @ApiModelProperty(value = "Precio final del pedido",name="finalPrice",dataType = "double",required=false,example="0.0")
    @Column(name = "final_Price")
    private double finalPrice;
    @ApiModelProperty(value = "Indica si el pedido esta listo para ser recogido",name="isReady",dataType = "boolean",required=false,example="false")
    @Column(name = "is_ready")
    private boolean isReady;
    
    @JsonIgnoreProperties("orders")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinTable(name = "Discount_Order", joinColumns = @JoinColumn(name="id_order",nullable = false), 
    		inverseJoinColumns = @JoinColumn(name="id_discount", nullable = false), uniqueConstraints = {
    				@UniqueConstraint(columnNames = {"id_order","id_discount"})
    		})
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY, targetEntity = Discount.class)
    private List<Discount> discounts;
    
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Document.class, orphanRemoval = true)
    private List<Document> documents;

    public Order() {
        this.id = -1L;
    }

    public Order(LocalDateTime pickupDate,LocalDateTime orderDate, User user, boolean isPayed, boolean isPickedUp, double finalPrice, List<Discount> discounts, List<Document> documents, boolean isReady) {
        this.id = -1L;
        this.pickupDate = pickupDate;
        this.orderDate = orderDate;
        this.user = user;
        this.isPayed = isPayed;
        this.isPickedUp = isPickedUp;
        this.finalPrice = finalPrice;
        this.discounts = discounts;
        this.documents = documents;
        this.isReady = isReady;
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

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }
}
