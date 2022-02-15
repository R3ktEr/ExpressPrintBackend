package com.iesfranciscodelosrios.model;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

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
    @ApiModelProperty(value = "Id del descuento",name="id",required=true,example="1")
    @Column(name = "id")
    private Long id;
    @ApiModelProperty(value = "nombre del descuento",name="name",required=true,example="oferta verano")
    @Column(name="name")
    private String name;
    @ApiModelProperty(value = "porcentage del descuento",name="percentage",required=false,example="15")
    //null si "isFixed" es true
    @Column(name="percentage")
    private Integer percentage;
    @ApiModelProperty(value = "valor fijo del descuento",name="fixedValue",required=false,example="7")
    //null si "isFixed" es false
    @Column(name="fixed_Value")
    private Integer fixedValue;
    @ApiModelProperty(value = "Si el valor del descuento es fijo o no",name="fixedValue",required=false,example="false")
    //"Es fijo el descuento?"
    @Column(name="is_fixed", nullable = false)
    private boolean isFixed;
    
    @JsonIgnoreProperties("discounts")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, 
    fetch = FetchType.EAGER, mappedBy = "discounts", targetEntity = Order.class)
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
