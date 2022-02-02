package com.iesfranciscodelosrios.model.price;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "ImpressionPerSide")
public class ImpressionPerSide extends Price implements Serializable {
    private enum ImpressionsTypes {
        normal(1),
        twoPages(2),
        twoSlides(3),
        fourSlides(4);

        private int icode;

        ImpressionsTypes(int icode) {
            this.icode = icode;
        }

        public int getICode() {
            return this.icode;
        }
    }

    @Serial
    private static final long serialVersionUID = 1L;
    @Column(name = "impressionsType")
    private ImpressionsTypes impressionsTypes;

    public ImpressionPerSide() {
        super();
    }

    public ImpressionsTypes getImpressionsTypes() {
        return impressionsTypes;
    }

    public void setImpressionsTypes(ImpressionsTypes impressionsTypes) {
        this.impressionsTypes = impressionsTypes;
    }

    @Override
    public String toString() {
        return "ImpressionPerSide{" + super.toString() + ", impressionsTypes=" + impressionsTypes + "}";
    }

}
