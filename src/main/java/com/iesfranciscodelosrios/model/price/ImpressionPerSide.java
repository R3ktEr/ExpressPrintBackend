package com.iesfranciscodelosrios.model.price;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import com.iesfranciscodelosrios.model.price.Enums.ImpressionsTypes;

@Entity
@Table(name = "Impression_per_side")
public class ImpressionPerSide extends Price implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Column(name = "impressions_type")
    private ImpressionsTypes impressionsTypes;

    public ImpressionPerSide(ImpressionsTypes impressionsTypes, float price, boolean valid){
        super();
        this.impressionsTypes = impressionsTypes;
        this.price = price;
        this.valid = valid;
    }

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
