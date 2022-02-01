package com.iesfranciscodelosrios.model.price;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "Copy")
public class Copy extends Price implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Column(name = "nCopys")
    private int nCopys;

    public Copy() {
        super();
    }

    public void setnCopys(int nCopys) {
        this.nCopys = nCopys;
    }

    public int getnCopys() {
        return nCopys;
    }

    @Override
    public String toString() {
        return "Copy{" + super.toString() + ", nCopys=" + nCopys + "}";
    }

}
