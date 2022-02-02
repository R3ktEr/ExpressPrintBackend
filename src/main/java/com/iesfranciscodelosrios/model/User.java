package com.iesfranciscodelosrios.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="_User")
public class User implements Serializable{
	@Serial
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="mail")
	private String mail;
	@Column(name="name")
	private String name;
	@Column(name="phoneNumber")
	private int phoneNumber;
	@Column(name="admin")
	private boolean admin;
	@Transient //One to many
	private List<Order> userOrders; 
	
	public User() {
		this.id=-1L;
	}
	
	public User(String mail, String name, int phoneNumber, boolean admin) {
		super();
		this.id = -1L;
		this.mail = mail;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.admin = admin;
	}
	
	public User(String mail, String name, int phoneNumber, boolean admin, List<Order> userOrders) {
		super();
		this.id = -1L;
		this.mail = mail;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.admin = admin;
		this.userOrders = userOrders;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public List<Order> getUserOrders() {
		return userOrders;
	}

	public void setUserOrders(List<Order> userOrders) {
		this.userOrders = userOrders;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", mail=" + mail + ", name=" + name + ", phoneNumber=" + phoneNumber + ", admin="
				+ admin + ", userOrders=" + userOrders + "]";
	}	
}
