package com.iesfranciscodelosrios.model.price;

import java.io.Serial;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Size")
public class Size extends Price implements Serializable {
    private enum EndedType {
        A3(0),
        A4(1),
        A5(2);

        private int icode;

        EndedType(int icode) {
            this.icode = icode;
        }

        public int getICode() {
            return this.icode;
        }
    }

    @Serial
    private static final long serialVersionUID = 1L;
    @Column(name = "endedType")
    private EndedType endedType;
    @Column(name = "sheetSize")
    private String sheetSize;

    public Size() {
        super();
    }

    public EndedType getEndedType() {
        return endedType;
    }

    public void setEndedType(EndedType endedType) {
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
