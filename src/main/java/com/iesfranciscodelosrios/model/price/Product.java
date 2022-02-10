package com.iesfranciscodelosrios.model.price;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
public abstract class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;
    @Column(name = "price")
    protected Float price;
    @Column(name = "valid")
    protected boolean valid;

    public Product() {
        this.id = -1L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public void setPrice(Float price) {
        this.price = price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product price = (Product) o;
        return Objects.equals(id, price.id);
    }

    @Override
    public String toString() {
        return "id=" + id + ", price=" + price;
    }

}