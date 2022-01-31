package com.iesfranciscodelosrios.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EndedPrice")
public class EndedPrice implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;
	@Column(name = "price")
	protected float price;
	@Column(name = "description")
	protected String description;
	@Column(name = "valid")
	protected boolean valid;

	public EndedPrice(Long id, float price, String description, boolean valid) {
		super();
		this.id = id;
		this.price = price;
		this.description = description;
		this.valid = valid;
	}

	public EndedPrice(float price, String description, boolean valid) {
		super();
		this.price = price;
		this.description = description;
		this.valid = valid;
	}

	public EndedPrice() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isvalid() {
		return valid;
	}

	public void setvalid(boolean valid) {
		this.valid = valid;
	}

	@Override
	public String toString() {
		return "SizePrice [id=" + id + ", price=" + price + ", description=" + description + ", valid=" + valid
				+ "]";
	}
}
