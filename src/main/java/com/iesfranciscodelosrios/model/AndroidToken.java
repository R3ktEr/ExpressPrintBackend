package com.iesfranciscodelosrios.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class AndroidToken implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @JsonIgnore
    private String token;

    public AndroidToken() {
    }

    public AndroidToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AndroidToken that = (AndroidToken) obj;
        if (!Objects.equals(token, that.token)) return false;
        return true;
    }
}
