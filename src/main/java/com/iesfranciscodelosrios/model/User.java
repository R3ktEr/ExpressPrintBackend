package com.iesfranciscodelosrios.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="_User")
public class User implements Serializable{
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "Id del usuario",name="id",required=false,example="1")
	@Column(name="id")
	private Long id;
	
	@ApiModelProperty(value = "Id del usuario de google",name="google_id",required=false,example="11341513")
	@Column(name="google_id")
	private String googleId;

	@ApiModelProperty(value = "Lista de tokens de android del usuario",name="androidTokens",required=false,example="Juan")
	@Column(name="androidTokens")
	@OneToMany(fetch = FetchType.LAZY)
	@JsonIgnore
	private List<AndroidToken> androidTokens;
	
	@ApiModelProperty(value = "Correo del usuario",name="mail",required=true,example="CorreoFalso@gmail.com")
	@Column(name="mail", unique = true)
	private String mail;
	
	@ApiModelProperty(value = "Nombre del usuario",name="name",required=false,example="Willian smith")
	@Column(name="name")
	private String name;
	
	@ApiModelProperty(value = "numero de telefono del usuario",name="phone_number",required=false,example="857432423")
	@Column(name="phone_number")

	private int phoneNumber; //Cambiar esto a String
	@ApiModelProperty(value = "Indicador de si el usuario tiene permisos de administrador",name="admin",required=false,example="false")
	@Column(name="admin")
	private boolean admin;
	@Column(name="is_disabled")
	private boolean isDisabled;

	@JsonIgnoreProperties("user")
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, 
	fetch = FetchType.LAZY, mappedBy = "user", targetEntity = Order.class)
	private List<Order> userOrders;
	
	public User() {
		this.id=-1L;
	}

	public User(String mail, String googleId, String name, int phoneNumber, boolean admin, boolean isDisabled) {
		super();
		this.id = -1L;
		this.googleId = googleId;
		this.mail = mail;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.admin = admin;
		this.isDisabled = isDisabled;
	}

	public User(String mail, String googleId, String name, int phoneNumber, boolean admin, boolean isDisabled, List<Order> userOrders) {
		super();
		this.id = -1L;
		this.googleId = googleId;
		this.mail = mail;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.admin = admin;
		this.isDisabled = isDisabled;
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

	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
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

	public boolean isDisabled() {
		return isDisabled;
	}

	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public List<Order> getUserOrders() {
		return userOrders;
	}

	public void setUserOrders(List<Order> userOrders) {
		this.userOrders = userOrders;
	}

	public List<AndroidToken> getAndroidTokens() {
		return androidTokens;
	}

	public void setAndroidTokens(List<AndroidToken> androidTokens) {
		this.androidTokens = androidTokens;
	}
}
