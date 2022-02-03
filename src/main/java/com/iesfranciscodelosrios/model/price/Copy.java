package com.iesfranciscodelosrios.model.price;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "Copy")
public class Copy extends Price implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public Copy() {
        super();
    }

    @Override
    public String toString() {
        return "Copy{" + super.toString() + "}";
    }

}
