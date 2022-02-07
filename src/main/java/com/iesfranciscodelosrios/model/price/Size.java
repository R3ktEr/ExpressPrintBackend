package com.iesfranciscodelosrios.model.price;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import com.iesfranciscodelosrios.model.price.Enums.sheetSize;

@Entity
@Table(name = "Size")
public class Size extends Price implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Column(name = "ended_type")
    private sheetSize endedType;
    @Column(name = "sheet_size")
    private String sheetSize;

    public Size(sheetSize endedType,String sheetSize, float price, boolean valid){
        super();
        this.endedType = endedType;
        this.sheetSize = sheetSize;
        this.price = price;
        this.valid = valid;
    }

    public Size() {
        super();
    }

    public sheetSize getEndedType() {
        return endedType;
    }

    public void setEndedType(sheetSize endedType) {
        this.endedType = endedType;
    }

    public String getSheetSize() {
        return sheetSize;
    }

    public void setSheetSize(String sheetSize) {
        this.sheetSize = sheetSize;
    }

    @Override
    public String toString() {
        return "Size{" + super.toString() + ", endedType=" + endedType + ", sheetSize=" + sheetSize + "}";
    }

}
