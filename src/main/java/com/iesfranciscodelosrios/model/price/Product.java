package com.iesfranciscodelosrios.model.price;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@MappedSuperclass
@ApiModel(subTypes = {Color.class, Copy.class, Ended.class, ImpressionPerSide.class, Size.class, Thickness.class})
public abstract class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "Id del producto",name="id",required=true,example="1")
    protected Long id;
    @Column(name = "price")
    @ApiModelProperty(value = "Precio del producto",name="price",required=true,example="1")
    protected Float price;
    @Column(name = "valid")
    @ApiModelProperty(value = "Validez del producto",name="valid",required=true,example="true")
    protected boolean valid;
    @Column(name = "date", nullable = false)
    @ApiModelProperty(value = "Fecha del precio del producto",name="date",required=true,example="01/01/1975")
    protected LocalDate date;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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