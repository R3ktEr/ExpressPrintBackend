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
@Table(name = "Ended")
public class Ended extends Price implements Serializable {
    private enum EndedType {
        no_ended(0),
        bound(1),
        stapled(2),
        twoHoles(3),
        fourHoles(4);

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

    public Ended() {
        super();
    }

    public EndedType getEndedType() {
        return endedType;
    }

    public void setEndedType(EndedType endedType) {
        this.endedType = endedType;
    }

    @Override
    public String toString() {
        return "Ended{" + super.toString() + ", endedType=" + endedType + "}";
    }

}
