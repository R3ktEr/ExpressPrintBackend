package com.iesfranciscodelosrios.model.price;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "PaperThickness")
public class Thickness extends Price implements Serializable {
    private enum ThicknessType {
        G80(0),
        G160(1),
        G280(2);

        private int icode;

        ThicknessType(int icode) {
            this.icode = icode;
        }

        public int getICode() {
            return this.icode;
        }
    }

    @Serial
    private static final long serialVersionUID = 1L;
    @Column(name = "thicknessType")
    private ThicknessType thicknessType;
    @Column(name = "description")
    private String description;

    public Thickness() {
        super();
    }

    public ThicknessType getThicknessType() {
        return thicknessType;
    }

    public void setThicknessType(ThicknessType thicknessType) {
        this.thicknessType = thicknessType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Thickness{" + super.toString() + ", thicknessType=" + thicknessType + ", description=" + description + "}";
    }

}