package com.iesfranciscodelosrios.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="Discount")
public class Discount implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name="name", nullable = false, unique = true)
    private String name;
    //null si "isFixed" es true
    @Column(name="percentage")
    private Integer percentage;
    //null si "isFixed" es false
    @Column(name="fixed_Value")
    private Integer fixedValue;
    //"Es fijo el descuento?"
    @Column(name="is_fixed", nullable = false)
    private boolean isFixed;
    
    @JsonIgnoreProperties("discounts")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "discounts", targetEntity = Order.class)
    private List<Order> orders;

    public Discount() {
        this.id = -1L;
    }

    public Discount(String name, Integer percentage, Integer fixedValue, boolean isFixed) {
        this.id = -1L;
        this.name = name;
        this.percentage = percentage;
        this.fixedValue = fixedValue;
        this.isFixed = isFixed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Integer getFixedValue() {
        return fixedValue;
    }

    public void setFixedValue(Integer fixedValue) {
        this.fixedValue = fixedValue;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return Objects.equals(id, discount.id);
    }

	@Override
	public String toString() {
		return "Discount [id=" + id + ", name=" + name + ", percentage=" + percentage + ", fixedValue=" + fixedValue
				+ ", isFixed=" + isFixed + ", orders=" + orders + "]";
	}
}
