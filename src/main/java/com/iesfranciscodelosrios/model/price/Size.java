package com.iesfranciscodelosrios.model.price;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

import com.iesfranciscodelosrios.model.price.Enums.sheetSize;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "Size")
public class Size extends Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "Enum de tamaño de la hoja", name = "sizeSheet", required = true, example = "A3", dataType = "String")
    @Column(name = "size_sheet")
    private sheetSize sizeSheet;
    @ApiModelProperty(value = "Descripción de tamaño de la hoja para saber cuanto mide en cm", name = "sheetSize", required = true, example = "300x300")
    @Column(name = "sheet_size")
    private String sheetSize;

    public Size(sheetSize endedType, String sheetSize, float price, boolean valid) {
        super();
        this.sizeSheet = endedType;
        this.sheetSize = sheetSize;
        this.price = price;
        this.valid = valid;
    }

    public Size() {
        super();
    }

    public sheetSize getSizeSheet() {
        return sizeSheet;
    }

    public void setSizeSheet(sheetSize endedType) {
        this.sizeSheet = endedType;
    }

    public String getSheetSize() {
        return sheetSize;
    }

    public void setSheetSize(String sheetSize) {
        this.sheetSize = sheetSize;
    }

    @Override
    public String toString() {
        return "Size{" + super.toString() + ", endedType=" + sizeSheet + ", sheetSize=" + sheetSize + "}";
    }

}
