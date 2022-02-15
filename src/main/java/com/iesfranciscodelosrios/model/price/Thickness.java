package com.iesfranciscodelosrios.model.price;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import com.iesfranciscodelosrios.model.price.Enums.ThicknessType;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "Thickness")
public class Thickness extends Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "Enum de grosor del folio", name = "thicknessType", required = true, example = "G80", dataType = "String")
    @Column(name = "thickness_type")
    private ThicknessType thicknessType;
    @ApiModelProperty(value = "Descripción del grosor del folio", name = "description", required = true, example = "Grosor tipo cartulina", dataType = "String")
    @Column(name = "description")
    private String description;

    public Thickness(ThicknessType thicknessType, String description, float price, boolean valid){
        super();
        this.thicknessType = thicknessType;
        this.description = description;
        this.price = price;
        this.valid = valid;
    }

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