package com.iesfranciscodelosrios.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

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
    @Column(name="fixedValue")
    private Integer fixedValue;
    //"Es fijo el descuento?"
    @Column(name="isFixed", nullable = false)
    private boolean isFixed;

    public Discount() {
        this.id = -1L;
    }

    public Discount(Long id, String name, Integer percentage, Integer fixedValue, boolean isFixed) {
        this.id = id;
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

}
