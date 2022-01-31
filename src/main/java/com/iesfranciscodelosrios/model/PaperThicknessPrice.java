package com.iesfranciscodelosrios.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PaperThicknessPrice")

public class PaperThicknessPrice {


	private static final long serialVersionUID=1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="price")
	private Float price;
	
	@Column(name="description")
	private String description;
	
	@Column(name="valid")
	private boolean valid;
	
	
	 public PaperThicknessPrice(Float price, String description, boolean valid) {
		super();
		this.id = -1L;
		this.price = price;
		this.description = description;
		this.valid = valid;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Float getPrice() {
		return price;
	}


	public void setPrice(Float price) {
		this.price = price;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public boolean isValid() {
		return valid;
	}


	public void setValid(boolean valid) {
		this.valid = valid;
	}


	@Override
	public String toString() {
		return "PaperThicknessPrice [id=" + id + ", price=" + price + ", description=" + description + ", valid="
				+ valid + "]";
	}
	 
	 
}