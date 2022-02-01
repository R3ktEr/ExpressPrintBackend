package com.iesfranciscodelosrios.model.price;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "Color")
public class Color extends Price implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Column(name = "isColor")
    private boolean isColor;

    public Color(Float price, boolean isColor, boolean valid) {
        super();
        this.id = -1L;
        this.price = price;
        this.isColor = isColor;
        this.valid = valid;
    }

    public Color() {
        super();
    }

    @Override
    public String toString() {
        return "Color{" + super.toString() + ", isColor=" + isColor + "}";
    }

}
