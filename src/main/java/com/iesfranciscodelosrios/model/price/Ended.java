package com.iesfranciscodelosrios.model.price;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import com.iesfranciscodelosrios.model.price.Enums.EndedType;

@Entity
@Table(name = "Ended")
public class Ended extends Price implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Column(name = "ended_type")
    private EndedType endedType;

    public Ended(EndedType endedType, float price, boolean valid){
        super();
        this.endedType = endedType;
        this.price = price;
        this.valid = valid;
    }

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
